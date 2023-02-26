import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.Random;

public class BefungeInterpreter {

    private enum Direction {
        UP, DOWN, LEFT, RIGHT;

        private static Random random = new Random();

        static Direction random() {
            Direction[] values = Direction.values();
            return values[random.nextInt(values.length)];
        }
    }

    private static class State {
        private static class Location {
            private static final int STARTING_X = 0;
            private static final int STARTING_Y = 0;
            private static final Direction STARTING_DIRECTION = Direction.RIGHT;

            private int x = STARTING_X;
            private int y = STARTING_Y;
            private Direction direction = STARTING_DIRECTION;
            private StringBuilder[] codePlane;

            Location(String[] codePlane) {
                this.codePlane = Arrays.stream(codePlane).map(StringBuilder::new).toArray(StringBuilder[]::new);
            }

            public char currentInstruction() {
                return getInstruction(x, y);
            }

            public char getInstruction(int x, int y) {
                return codePlane[y].charAt(x);
            }

            public void setInstruction(int x, int y, char instruction) {
                codePlane[y].setCharAt(x, instruction);
            }

            public void setDirection(Direction direction) {
                this.direction = direction;
            }

            private void move() {
                switch (direction) {
                    case UP:
                        moveUp();
                        break;
                    case DOWN:
                        moveDown();
                        break;
                    case LEFT:
                        moveLeft();
                        break;
                    case RIGHT:
                        moveRight();
                        break;
                }
            }

            private void moveUp() {
                if(y == 0) {
                    y = codePlane.length - 1;
                } else {
                    y--;
                }
            }

            private void moveDown() {
                if(y == codePlane.length - 1) {
                    y = 0;
                } else {
                    y++;
                }
            }

            private void moveLeft() {
                if(x == 0) {
                    x = codePlane[y].length() - 1;
                } else {
                    x--;
                }
            }

            private void moveRight() {
                if(x == codePlane[y].length() - 1) {
                    x = 0;
                } else {
                    x++;
                }
            }
        }

        private Location location;

        private boolean stringMode = false;
        private Deque<Integer> stack = new ArrayDeque<>();
        private StringBuilder output = new StringBuilder();
        private boolean done = false;

        public State(String code) {
            location = new Location(code.split("\n"));
        }

        public char currentInstruction() {
            return location.currentInstruction();

        }

        public String output() {
            return output.toString();
        }

        public boolean done() {
            return done;
        }

        public void processInstruction(char instruction) {
            if (stringMode) {
                if (instruction == '"') {
                    flipStringMode();
                } else {
                    instructionActiveStringMode(instruction);
                }
            } else {
                switch (instruction) {
                    case '0':
                    case '1':
                    case '2':
                    case '3':
                    case '4':
                    case '5':
                    case '6':
                    case '7':
                    case '8':
                    case '9':
                        instructionNumber(instruction);
                        break;
                    case '+':
                        instructionPlus();
                        break;
                    case '-':
                        instructionMinus();
                        break;
                    case '*':
                        instructionMultiply();
                        break;
                    case '/':
                        instructionDivide();
                        break;
                    case '%':
                        instructionModulus();
                        break;
                    case '!':
                        instructionNot();
                        break;
                    case '`':
                        instructionGreaterThan();
                        break;
                    case '>':
                        instructionRight();
                        break;
                    case '<':
                        instructionLeft();
                        break;
                    case '^':
                        instructionUp();
                        break;
                    case 'v':
                        instructionDown();
                        break;
                    case '?':
                        instructionRandom();
                        break;
                    case '_':
                        instructionHorizontal();
                        break;
                    case '|':
                        instructionVertical();
                        break;
                    case '"':
                        flipStringMode();
                        break;
                    case ':':
                        instructionDuplicate();
                        break;
                    case '\\':
                        instructionSwap();
                        break;
                    case '$':
                        instructionDiscard();
                        break;
                    case '.':
                        instructionOutputInt();
                        break;
                    case ',':
                        instructionOutputAscii();
                        break;
                    case '#':
                        instructionTrampoline();
                        break;
                    case 'p':
                        instructionPut();
                        break;
                    case 'g':
                        instructionGet();
                        break;
                    case '@':
                        instructionEnd();
                        break;
                    case ' ': // No-op
                    default:
                        // Do nothing

                }
            }
            location.move();
        }

        private void flipStringMode() {
            stringMode = !stringMode;
        }

        private void instructionNumber(char instruction) {
            stack.push(instruction - '0');
        }

        private void instructionPlus() {
            int a = stack.pop();
            int b = stack.pop();
            stack.push(a + b);
        }

        private void instructionMinus() {
            int a = stack.pop();
            int b = stack.pop();
            stack.push(b - a);
        }

        private void instructionMultiply() {
            int a = stack.pop();
            int b = stack.pop();
            stack.push(a * b);
        }

        private void instructionDivide() {
            int a = stack.pop();
            int b = stack.pop();
            stack.push(a == 0 ? 0 : b / a);
        }

        private void instructionModulus() {
            int a = stack.pop();
            int b = stack.pop();
            stack.push(a == 0 ? 0 : b % a);
        }

        private void instructionNot() {
            int a = stack.pop();
            stack.push(a == 0 ? 1 : 0);
        }

        private void instructionGreaterThan() {
            int a = stack.pop();
            int b = stack.pop();
            stack.push(b > a ? 1 : 0);
        }

        private void instructionRight() {
            location.setDirection(Direction.RIGHT);
        }

        private void instructionLeft() {
            location.setDirection(Direction.LEFT);
        }

        private void instructionUp() {
            location.setDirection(Direction.UP);
        }

        private void instructionDown() {
            location.setDirection(Direction.DOWN);
        }

        private void instructionRandom() {
            location.setDirection(Direction.random());
        }

        private void instructionHorizontal() {
            if (stack.pop().intValue() == 0) {
                location.setDirection(Direction.RIGHT);
            } else {
                location.setDirection(Direction.LEFT);
            }
        }

        private void instructionVertical() {
            if (stack.pop().intValue() == 0) {
                location.setDirection(Direction.DOWN);
            } else {
                location.setDirection(Direction.UP);
            }
        }

        private void instructionActiveStringMode(char instruction) {
            stack.push((int) instruction);
        }

        private void instructionDuplicate() {
            stack.push(stack.isEmpty() ? 0 : stack.peek());
        }

        private void instructionSwap() {
            int a = stack.pop();
            int b = stack.isEmpty() ? 0 : stack.pop();
            stack.push(a);
            stack.push(b);
        }

        private void instructionDiscard() {
            stack.pop();
        }

        private void instructionOutputInt() {
            output.append(stack.pop());
        }

        private void instructionOutputAscii() {
            output.append((char) stack.pop().intValue());
        }

        private void instructionTrampoline() {
            location.move();
        }

        private void instructionPut() {
            int y = stack.pop();
            int x = stack.pop();
            char newInstruction = (char) stack.pop().intValue();
            location.setInstruction(x, y, newInstruction);
        }

        private void instructionGet() {
            int y = stack.pop();
            int x = stack.pop();
            stack.push((int)location.getInstruction(x, y));
        }

        private void instructionEnd() {
            done = true;
        }
    }

    public String interpret(String code) {

        State state = new State(code);

        for (char instruction = state.currentInstruction(); !state.done(); instruction = state.currentInstruction()) {
            state.processInstruction(instruction);
        }

        return state.output();
    }
}