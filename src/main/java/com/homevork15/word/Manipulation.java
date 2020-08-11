package com.homevork15.word;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class Manipulation {
    private final String sourceFileName = "data.txt";
    private final String censoredFileName = "delete.txt";

    public void start() {
        File source = getFileFromResources(sourceFileName);
        List<String> allWords = getWordsFromFile(source);
        System.out.printf("Word count: %d\n", allWords.size());
        System.out.printf("Uncensored words  %d\n",
                getUncensoredWords(allWords).size());
        List<String> censoredLongWords =
                getCensoredWords(getLongWordsFromList(allWords, 3));
        System.out.print("Most popular words: ");
        printWordList(getMostPopularWords(censoredLongWords, 5));
    }

    private List<String> getWordsFromFile(File file) {
        Scanner reader = null;
        try {
            reader = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        StringBuilder sb = new StringBuilder();
        while (true) {
            assert reader != null;
            if (!reader.hasNext()) break;
            sb.append(reader.nextLine());
            sb.append("\n");
        }
        reader.close();
        return getWordsFromString(sb.toString());
    }

    private List<String> getWordsFromString(final String string) {
        return Arrays.stream(
                string.replaceAll("[()!,.]", "")
                        .toLowerCase()
                        .split("[\\n\\s]+"))
                .collect(Collectors.toList());
    }

    private List<String> getUncensoredWords(List<String> list) {
        File file = getFileFromResources(censoredFileName);
        List<String> uncensoredWords = getWordsFromFile(file);
        return list.stream()
                .filter(uncensoredWords::contains)
                .collect(Collectors.toList());
    }

    private List<String> getCensoredWords(List<String> list) {
        List<String> uncensoredWords = getUncensoredWords(list);
        return list.stream()
                .filter(s -> !uncensoredWords.contains(s))
                .collect(Collectors.toList());
    }

    private List<String> getLongWordsFromList(List<String> list, int shortLength) {
        return list.stream()
                .filter(s -> s.length() > shortLength)
                .collect(Collectors.toList());
    }

    private List<String> getMostPopularWords(List<String> list, int quantity) {
        Map<String, Integer> map = new HashMap<>();
        list.forEach(s -> {
            if (map.containsKey(s)) {
                int value = map.get(s);
                map.put(s, value + 1);
            } else {
                map.put(s, 1);
            }
        });
        List<WordText> words = new ArrayList<>();
        map.forEach((s, integer) -> words.add(new WordText(s, integer)));
        words.sort(Comparator.comparing(WordText::getCount).reversed());
        return words.stream()
                .map(WordText::getString)
                .limit(quantity)
                .collect(Collectors.toList());
    }

    private void printWordList(List<String> list) {
        String result = String.join(", ", list.toArray(new CharSequence[0]));
        System.out.println(result);
    }

    private File getFileFromResources(String string) {
        ClassLoader classLoader = Manipulation.class.getClassLoader();
        return new File(Objects.requireNonNull(
                classLoader.getResource(string)).getFile());
    }
}
