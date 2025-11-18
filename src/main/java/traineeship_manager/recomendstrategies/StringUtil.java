package traineeship_manager.recomendstrategies;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StringUtil {

    public static List<String> parseStringToList(String input) {
        if (input == null || input.trim().isEmpty()) {
            return List.of();
        }
        return Arrays.stream(input.split(","))
                .map(String::trim)
                .map(String::toLowerCase)
                .collect(Collectors.toList());
    }
    
    public static boolean isSubset(List<String> a, List<String> b) {
        return b.containsAll(a);
    }
    
    public static boolean hasOverlap(List<String> a, List<String> b) {
        return a.stream().anyMatch(b::contains);
    }
}
