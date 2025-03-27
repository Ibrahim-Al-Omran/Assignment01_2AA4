package ca.mcmaster.se2aa4.mazerunner;

public class WalkerFactory {
    
    public static Walker createWalker(String method, String filename) {
        return switch (method.toLowerCase()) {
            case "tremaux" -> new Tremaux(filename);
            case "righthand", "default" -> new RightHand(filename);
            default -> throw new IllegalArgumentException("Unknown method: " + method);
        };
    }
    
    public static Walker createDefaultWalker(String filename) {
        return new RightHand(filename);
    }
}
