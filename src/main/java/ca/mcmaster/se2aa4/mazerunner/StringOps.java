package ca.mcmaster.se2aa4.mazerunner;

public class StringOps {
    public String factorize(String path) {
        if (path == null || path.isEmpty()) {
            return path; // Handle empty or null inputs
        }
    
        StringBuilder newPath = new StringBuilder();
        int count = 1;
        char prev = path.charAt(0);
    
        for (int i = 1; i < path.length(); i++) {
            if (path.charAt(i) == prev) {
                count++;
            } else {
                if (count == 1) {
                    newPath.append(prev);
                } else {
                    newPath.append(count).append(prev);
                }
                count = 1;
                prev = path.charAt(i);
            }
        }
    
        // Append the last character or its count
        if (count == 1) {
            newPath.append(prev);
        } else {
            newPath.append(count).append(prev);
        }
    
        return newPath.toString();
    }
    
    public String convertFactorized(String path) {
        if (path == null || path.isEmpty()) {
            return path; 
        }
    
        StringBuilder newPath = new StringBuilder();
        int i = 0;
    
        while (i < path.length()) {
            char c = path.charAt(i);
    
            // Check if the current character is a digit
            if (Character.isDigit(c)) {
                // Extract the full count
                int count = 0;
                while (i < path.length() && Character.isDigit(path.charAt(i))) {
                    count = count * 10 + Character.getNumericValue(path.charAt(i));
                    i++;
                }
    
                // Append the character count times
                if (i < path.length()) {
                    char ch = path.charAt(i);
                    for (int j = 0; j < count; j++) {
                        newPath.append(ch);
                    }
                    i++;
                } else {
                    throw new IllegalArgumentException("Error -- Invalid factorized path");
                }
            } else {
                // Append character as-is
                newPath.append(c);
                i++;
            }
        }
    
        return newPath.toString();
    }
}
