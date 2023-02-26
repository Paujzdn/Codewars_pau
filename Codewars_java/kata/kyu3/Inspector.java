package kata.kyu3;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Inspector {
    private Map<String,ArrayList<String>> val = new HashMap<>();
    private final Map<String, String> cmn_rx_ptts =  new HashMap<>();
    private final Map<String, String> sp_rx_ptt =  new HashMap<>();
    private final ArrayList<String> sig = new ArrayList<>();

    //By country requirements
    private final ArrayList<String> f_req = new ArrayList<>();
    private final ArrayList<String> c_req = new ArrayList<>();
    private final ArrayList<String> e_req = new ArrayList<>();
    private final ArrayList<String> allw_n  = new ArrayList<>();

    //other requirements
    private final Map<String,ArrayList<String>> required_vaccinations  = new HashMap<>();
    private final ArrayList<String> workers_requirements = new ArrayList<>();
    private String wanted_by_the_state="";

    //other utility objects
    final private LocalDate Date = LocalDate.of(1982,11,22);
    private Matcher match;
    private Pattern patt;
    public Inspector() {
        cmn_rx_ptts.put("ISS","ISS: ([A-Z][a-z]+.?\\s*[A-Z]*[a-z]*)\n+");
        cmn_rx_ptts.put("WEIGHT","WEIGHT: (\\d{2,3}");
        cmn_rx_ptts.put("HEIGHT","HEIGHT: (\\d{2,3}");
        cmn_rx_ptts.put("DURATION","DURATION: (\\d+ [A-Z]+|[A-Z]+)");
        cmn_rx_ptts.put("PURPOSE","PURPOSE: ([A-Z]+)");
        cmn_rx_ptts.put("ID number","ID#: (\\w*-*\\w*)");
        cmn_rx_ptts.put("FIELD","FIELD: ([A-Z][a-z]+\\s*[a-z]*)");
        cmn_rx_ptts.put("DOB","DOB: (\\d{4}\\.+\\d{2}\\.+\\d{2})");
        cmn_rx_ptts.put("SEX","SEX: (F|M)");
        cmn_rx_ptts.put("nationality","NATION: ([A-Z][a-z]+\\s*[A-Z]*[a-z]*)\n+");
        sp_rx_ptt.put("name","NAME: ([A-Z]\\w+),+\\s([A-Z][a-z]+)");
        sp_rx_ptt.put("EXP","EXP: (\\d{4}\\.+\\d{2}\\.+\\d{2})");
        sig.addAll(cmn_rx_ptts.keySet()); sig.addAll(sp_rx_ptt.keySet());
        sig.add("DOCUMENT"); sig.add("ACCESS"); sig.add("VACCINES");
    }
    private void change(String word, String regex, ArrayList<String> col)
    {
        patt = Pattern.compile(regex);
        match = patt.matcher(word);
        match.find();
        col.add(match.group(1));
    }
    public void receiveBulletin(String bulletin) {
        String[] re = bulletin.split("\n");
        for(String g:re){
            if(g.matches("Allow citizens of.+")){
                patt = Pattern.compile("(?:Allow citizens of)? ([A-Z][a-z]+\\s*[A-Z]*[a-z]*),*");
                match=patt.matcher(g);
                while (match.find()) {
                    allw_n.add(match.group(1));
                }
            }else if (g.matches("Deny citizens of.+")) {

                patt = Pattern.compile("(?: Deny citizens of)? ([A-Z][a-z]+\\s*[A-Z]*[a-z]*),*");
                match = patt.matcher(g);
                while (match.find()) {
                    allw_n.remove(match.group(1));
                }
            } else if (g.matches("\\D+ no longer require (\\w+\\s?\\w*) vaccination")) {

                patt = Pattern.compile("\\D+ no longer require (\\w+\\s?\\w*) vaccination");
                match = patt.matcher(g);
                match.find();

                String vaccination = match.group(1);
                ArrayList<String> nations = new ArrayList<>();

                patt  = Pattern.compile("(?:^Citizens of|(?!^)\\G,) ([A-Z][a-z]+(?: [A-Z][a-z]+)*)(?=[a-zA-Z, ]*?)");
                match = patt.matcher(g);
                while (match.find())
                    nations.add(match.group(1));

                if(!nations.isEmpty()) {
                    while (!nations.isEmpty()) {
                        required_vaccinations.get(vaccination).remove(nations.remove(0));
                    }
                }
                else if (g.contains("Foreigners"))
                    required_vaccinations.get(vaccination).remove("FOREIGNERS");
                else
                    required_vaccinations.get(vaccination).remove("ENTRANTS");


            } else if (g.matches("\\D+ require (\\w+\\s?\\w*) vaccination")) {

                //thanks to: https://stackoverflow.com/users/548225/anubhava for commitment in this regex pattern
                patt  = Pattern.compile("(?:^Citizens of|(?!^)\\G,) ([A-Z][a-z]+(?: [A-Z][a-z]+)*)(?=[a-zA-Z, ]*?)");
                match = patt.matcher(g);

                ArrayList<String> nations = new ArrayList<>();
                while (match.find())
                    nations.add(match.group(1));

                patt = Pattern.compile("\\D+ require (\\w+\\s?\\w*) vaccination");
                match = patt.matcher(g);
                match.find();
                if(!nations.isEmpty())
                    required_vaccinations.put(match.group(1), nations);
                else if (g.contains("Foreigners"))
                {
                    nations.add("FOREIGNERS");
                    required_vaccinations.put(match.group(1),nations);
                }
                else
                {
                    nations.add("ENTRANTS");
                    required_vaccinations.put(match.group(1),nations);

                }

            } else if (g.matches("Foreigners require \\D+")) {
                change(g, "(?:Foreigners require) (\\D+)", f_req);
            } else if (g.matches("Workers require \\D+")) {
                change(g, "(?:Workers require) (\\D+)", workers_requirements);
            } else if (g.matches("Citizens of Arstotzka require \\D+")) {
                change(g, "(?:Citizens of Arstotzka require) (\\D+)", c_req);
            } else if (g.matches("Wanted by the State: \\D+")) {
                Pattern pattern = Pattern.compile(("(?:Wanted by the State: )(\\D+)"));
                Matcher matcher = pattern.matcher(g);
                matcher.find();
                wanted_by_the_state = matcher.group(1);
            } else if (g.matches("Entrants require \\D+")) {
                change(g, "(?:Entrants require) (\\D+)", e_req);
            }
        }
    }

    private String check(ArrayList<String> v, String type)
    {
        if (type.equals("DOCUMENT") || type.equals("EXP") || type.equals("ACCESS") || type.equals("VACCINES"))
            return "";
        if(v.stream().distinct().count() > 1)
            return "Detainment: " + type + " mismatch.";
        return "";
    }


    private String vaccineCheck()
    {
        ArrayList<String> vaccineSet = new ArrayList<>(required_vaccinations.keySet());

        for (String s : vaccineSet) {
            ArrayList<String> nations = required_vaccinations.get(s);
            for (String nation : nations) {

                if ((nation.equals("FOREIGNERS") && !val.get("nationality").get(0).equals("Arstotzka")) || nation.equals("ENTRANTS") || nation.equals(val.get("nationality").get(0))) {
                    if (!val.get("DOCUMENT").contains("certificate of vaccination"))
                        return "Entry denied: missing required certificate of vaccination.";
                    if (val.get("VACCINES").get(0).contains(s))
                        continue;
                    return "Entry denied: missing required vaccination.";
                }
            }
        }
        return "";
    }

    private String expChecking(String date)
    {
        if(date == null)
            return "";

        LocalDate localDate = LocalDate.parse(date.substring(0,10), DateTimeFormatter.ofPattern("yyyy.MM.dd"));
        if(localDate.isAfter(Date) || localDate.isEqual(Date))
            return "";
        return  "Entry denied: " + date.substring(11) + " expired.";

    }


    public String inspect(Map<String,String> a )
    {
        resetValues();


        for(String ks : a.keySet())
            this.setDocument(ks,a.get(ks));

        if (val.get("name").contains(wanted_by_the_state))
            return "Detainment: Entrant is a wanted criminal.";
        for(int i = 0; i < val.size(); i++)
        {
            String test = check(val.get(sig.get(i)),sig.get(i));
            if(!test.isEmpty())
                return test;
        }


        for(int i = 0 ; i < val.get("EXP").size(); i++)
        {
            String test =  expChecking(val.get("EXP").get(i));
            if(!test.equals("")) {
                return test;
            }

        }
        for (String entrants_requirement : e_req) {
            if (!val.get("DOCUMENT").contains(entrants_requirement)) {
                return "Entry denied: missing required " + entrants_requirement + ".";
            }
        }

        if (!val.get("nationality").get(0).equals("Arstotzka"))
        {
            for (String foreigners_requirement : f_req)
                if (!val.get("DOCUMENT").contains(foreigners_requirement)) {
                    if (foreigners_requirement.equals("access permit")) {
                        if (val.get("DOCUMENT").contains("diplomatic authorization")) {
                            if (!val.get("ACCESS").get(0).contains("Arstotzka"))
                                return "Entry denied: invalid diplomatic authorization.";
                            else
                                continue;
                        } else if (val.get("DOCUMENT").contains("grant of asylum"))
                            continue;
                        else
                            return "Entry denied: missing required " + foreigners_requirement + ".";
                    }
                    return "Entry denied: missing required " + foreigners_requirement + ".";
                }
        }
        if(!allw_n.contains(val.get("nationality").get(0)))
            return "Entry denied: citizen of banned nation.";

        if(val.get("PURPOSE").contains("WORK") && workers_requirements.contains("work pass"))
        {
            if(!val.get("DOCUMENT").contains("work pass"))
                return "Entry denied: missing required work pass.";
        }

        String vaccine =  vaccineCheck();
        if(!vaccine.isEmpty())
            return vaccine;



        if(val.get("nationality").get(0).equals("Arstotzka") && c_req.contains("ID card") && !val.get("DOCUMENT").contains("ID card"))
            return "Entry denied: missing required ID card.";

        if(val.get("nationality").get(0).equals("Arstotzka")) {
            return "Glory to Arstotzka.";
        }


        return "Cause no trouble.";


    }

    private void setDocument(String docName, String document)
    {
        String doc = docName.replace("_"," ");
        val.get("DOCUMENT").add(doc);
        getName(document);
        getDate(doc,document);
        if(docName.equals("diplomatic_authorization"))
        {

            val.get("ACCESS").add(document);

        }
        if(docName.equals("certificate_of_vaccination"))
        {
            val.get("VACCINES").add(document);
        }
        for (String patterns : cmn_rx_ptts.keySet())
        {
            patt = Pattern.compile(cmn_rx_ptts.get(patterns));
            match = patt.matcher(document);
            if(match.find())
                val.get(patterns).add(match.group(1));
        }

    }
    private void getDate(String docName, String document)
    {
        patt = Pattern.compile(sp_rx_ptt.get("EXP"));
        match = patt.matcher(document);
        if(match.find())
            val.get("EXP").add(match.group(1) + " " + docName);
    }
    private void getName(String name)
    {
        patt = Pattern.compile(sp_rx_ptt.get("name"));
        match = patt.matcher(name);
        if(match.find())
            val.get("name").add(match.group(2)+" "+match.group(1));
    }

    private void resetValues()
    {
        val = new HashMap<>();
        for (String s : sig) val.put(s, new ArrayList<>());
    }

}