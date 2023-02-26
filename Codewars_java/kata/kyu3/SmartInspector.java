package kata.kyu3;
import java.util.*;
import java.util.regex.*;
import java.util.stream.*;
import java.text.*;


public class SmartInspector {

    final private static boolean DEBUG = true;

    final static private String           NATION      = "arstotzka";
    final static private SimpleDateFormat DATE_PARSER = new SimpleDateFormat("yyyy.MM.dd");
    final static private Date             EXPIRE_DT   = getDate("1982.11.22");

    final private static Pattern
            P_PAPERS      = Pattern.compile("([^:]+): (.+)\\n?"),
            P_CONSTRAINTS = Pattern.compile("wanted by the state: (?<wanted>.+)|" +
                    "(?<action>allow|deny) citizens of (?<who>(?:[\\w ]+|, )+)|" +
                    "(?:citizens of )?(?<who2>.+?)(?<noMore> no longer)? require (?<piece>[\\w ]+)");

    final static private String[]
            COUNTRIES     = {"arstotzka", "antegria", "impor", "kolechia", "obristan", "republia", "united federation"},
            FOREIGNERS    = Stream.of(COUNTRIES).filter( c -> !NATION.equals(c) ).toArray(String[]::new);

    final static private Map<String,String>
            MISMATCHER    = new HashMap<String,String>() {{
        put("NAME", "name");
        put("NATION", "nationality");
        put("ID#", "ID number");
        put("DOB", "date of birth");
    }};

    final private static Set<String>
            ACCESS_SUBSTITUTES = new HashSet<String>(Arrays.asList("grant_of_asylum", "diplomatic_authorization", "access_permit"));


    // Instance fields:
    private Map<String,Boolean>      allowed = Arrays.stream(COUNTRIES).collect(Collectors.toMap(k->k, k->false));                        // Map of nations that are authorized or not to cross the border.
    private Map<String,Set<String>>  docs    = Arrays.stream(COUNTRIES).collect(Collectors.toMap(k->k, k->new HashSet<String>())),
            vacs    = Arrays.stream(COUNTRIES).collect(Collectors.toMap(k->k, k->new HashSet<String>()));        // Documents needed per nation and related vaccines if needed

    // Instance fields dedicated to the inspect or bulletin methods:
    private Set<String>                    wanted = null;
    private Map<String,Map<String,String>> papers = null;



    public void receiveBulletin(String bulletin) {

        wanted    = null;
        Matcher m = P_CONSTRAINTS.matcher(bulletin.toLowerCase());

        while (m.find()) {

            if (m.group("wanted") != null) {
                wanted = getWantedSet(m.group("wanted"));
                continue;
            }

            String[] whos = (m.group("who")!=null ? m.group("who"):m.group("who2")).split(", ");
            if      ("entrants".equals(  whos[0])) whos = COUNTRIES;
            else if ("foreigners".equals(whos[0])) whos = FOREIGNERS;

            if (m.group("action") != null) {
                for (String country: whos)
                    allowed.put(country, m.group("action").equals("allow"));

            } else {
                boolean toRemove = m.group("noMore") != null;
                String  piece    = m.group("piece").replace(" ","_");
                Map<String,Set<String>> stuffToUpdate = docs;

                if (piece.endsWith("vaccination")) {
                    stuffToUpdate = vacs;
                    piece = piece.replace("_vaccination", "");
                } else if ("id_card".equals(piece)) {
                    piece = "ID_card";
                }

                for (String who: whos) {
                    Set<String> map = stuffToUpdate.computeIfAbsent(who, k -> new HashSet<String>());
                    if (toRemove) map.remove(piece);
                    else          map.add(piece);
                }
            }
        }
    }


    private static Set<String> getWantedSet(String s) { return Arrays.stream( s.replace(",","").split(" ") ) .collect(Collectors.toSet()); }

    private static Date getDate(String s)             { return DATE_PARSER.parse(s, new ParsePosition(0)); }


    public String inspect(Map<String,String> person) {

        definePapers(person);

        String      nation      = extractNation(),
                mismatched  = getAnyMismatched(),
                expiredDoc  = getAnyExpiredDoc();
        boolean     isForeign   = !NATION.equals(nation),
                isBadDiplo  = false;
        Set<String> missingDocs = getMissingDocs(nation),
                missingVacs = getMissingVaccines(nation);

        if (isForeign && missingDocs.contains("access_permit")) {
            Set<String> substitutes = papers.keySet().stream()
                    .filter( p -> ACCESS_SUBSTITUTES.contains(p) )
                    .collect(Collectors.toSet());

            if (!substitutes.isEmpty()) missingDocs.remove("access_permit");

            isBadDiplo = substitutes.contains("diplomatic_authorization")
                    && substitutes.size()==1
                    && !papers.get("diplomatic_authorization").get("ACCESS").contains(NATION);
        }


        if (isWanted())               return "Detainment: Entrant is a wanted criminal.";
        if (mismatched != null)       return String.format("Detainment: %s mismatch.", mismatched);
        if (!missingDocs.isEmpty())   return String.format("Entry denied: missing required %s.", missingDocs.stream().findAny().get().replace("_", " "));
        if (isBadDiplo)               return "Entry denied: invalid diplomatic authorization.";
        if (!allowed.get(nation))     return "Entry denied: citizen of banned nation.";
        if (expiredDoc != null)       return String.format("Entry denied: %s expired.", expiredDoc.replace("_", " "));
        if (!missingVacs.isEmpty())   return "Entry denied: missing required vaccination.";

        return isForeign ? "Cause no trouble." : "Glory to Arstotzka.";
    }



    private void definePapers(Map<String,String> person) {

        papers = new HashMap<String,Map<String,String>>();
        for (String p: person.keySet()) {
            Matcher m = P_PAPERS.matcher(person.get(p));

            while (m.find()) {
                papers.computeIfAbsent(p, k -> new HashMap<String,String>())
                        .put(m.group(1), m.group(2).toLowerCase());
            }
        }
    }


    private boolean isWanted() {
        return papers.values().stream()
                .map( m -> getWantedSet(m.getOrDefault("NAME", "")) )
                .anyMatch( ss -> ss.equals(wanted) );
    }


    private String extractNation() {
        return papers.values().stream()
                .filter( p -> p.containsKey("NATION") )
                .map(    p -> p.get("NATION") )
                .findAny()
                .orElse("");
    }


    private String getAnyMismatched() {
        List<String> lst = new ArrayList<>(papers.keySet());

        for (int i=0 ; i<lst.size() ; i++)
            for (int j=i+1 ; j<lst.size() ; j++) {

                final Map<String,String> p1 = papers.get(lst.get(i)),
                        p2 = papers.get(lst.get(j));
                String looser = p1.keySet().stream()
                        .filter(k -> p2.containsKey(k) && !k.equals("EXP") && !p1.get(k).equals(p2.get(k)) )
                        .findAny()
                        .orElse(null);

                if (looser!=null) return MISMATCHER.get(looser);
            }
        return null;
    }


    private String getAnyExpiredDoc() {
        return papers.entrySet().stream()
                .filter( me -> me.getValue().containsKey("EXP") && !getDate(me.getValue().get("EXP")).after(EXPIRE_DT) )
                .map(Map.Entry::getKey)
                .findAny()
                .orElse(null);
    }


    private Set<String> getMissingDocs(String nation) {
        Set<String> required = Stream.concat(Stream.of("passport"), docs.getOrDefault(nation, new HashSet<String>()).stream())
                .collect(Collectors.toSet());

        if (!nation.isEmpty() && !vacs.get(nation).isEmpty()) required.add("certificate_of_vaccination");
        if (needWorkPass())                                   required.add("work_pass");

        return required.stream().filter( d -> !papers.containsKey(d) ).collect(Collectors.toSet());
    }


    private boolean needWorkPass() {
        return docs.containsKey("workers")
                && papers.getOrDefault("access_permit", new HashMap<String,String>())
                .getOrDefault("PURPOSE", "")
                .equals("work");
    }


    private Set<String> getMissingVaccines(String nation) {

        String[] vaccines =  papers.getOrDefault("certificate_of_vaccination", new HashMap<String,String>())
                .getOrDefault("VACCINES", "")
                .replace(" ","_")
                .split(",_");
        Set<String> got = Arrays.stream(vaccines).collect(Collectors.toSet());

        return vacs.getOrDefault(nation, new HashSet<String>())
                .stream()
                .filter( v -> !got.contains(v) )
                .collect(Collectors.toSet());
    }
}