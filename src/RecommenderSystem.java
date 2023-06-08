import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.*;

public class RecommenderSystem {
    private List<String> titles;
    private MovieNode overallRoot;
    public RecommenderSystem() {
        overallRoot = new MovieList("movies.txt").getOutputRoot();
        titles = new ArrayList<String>();
        MovieNode current = overallRoot;
        while (current.next != null) {
            titles.add(current.data.getTitle());
            current = current.next;
        }
    }
    public List<String> getTitles() {
        return titles;
    }

    public MovieNode getOverallRoot() {
        return overallRoot;
    }
    public MovieNode find(String key) {
        MovieNode current = overallRoot;
        while (current != null) {
            if (current.data.getTitle().equalsIgnoreCase(key)) {
                return current;
            }
            current = current.next;
        }
        return null;
    }
    public void add(Movie movie) {
        MovieNode current = overallRoot;
        while (current.next != null) {
            current = current.next;
        }
        current.next = new MovieNode(movie);
        titles.add(movie.getTitle());
        try {
            PrintStream out = new PrintStream(new FileOutputStream("movies.txt", true));
            out.println();
            out.print(movie.toString());
            out.close();
        }
        catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
    public void remove(String title) {
        if (overallRoot.data.getTitle().equalsIgnoreCase(title)) {
            overallRoot = overallRoot.next;
            titles.remove(0);
        }
        MovieNode current = find(title);
        MovieNode previous = overallRoot;
        while (previous.next != current) {
            previous = previous.next;
        }
        previous.next = current.next;
        titles.remove(title);
    }
    public List<String> search(String key) {
        List<String> result = new ArrayList<>();
        MovieNode current = overallRoot;
        while (current.next != null) {
            if (current.data.getTitle().toLowerCase().contains(key.toLowerCase())) {
                result.add(current.data.getTitle());
            }
            current = current.next;
        }
        return result;
    }
    public List<String> filter(String answer) {
        List<String> list = new LinkedList<String>();
        if (answer.equalsIgnoreCase("All")) {
            return titles;
        }
        MovieNode current = overallRoot;
        while (current.next != null) {
            if (current.data.getGenre().equalsIgnoreCase(answer)) {
                list.add(current.data.getTitle());
            }
            current = current.next;
        }
        return list;
    }
//        public List<String> sortByTitle() {
//        int j;
//        for (int i = 0; i < titles.size(); i++) {
//            j = i - 1;
//            String temp = titles.get(i);
//            while (j >= 0 && titles.get(j).compareToIgnoreCase(temp) > 0) {
//                titles.set(j + 1, titles.get(j));
//                j--;
//            }
//            titles.set(j + 1, temp);
//        }
//        return titles;
//    }
    public List<String> generate(String answer) {
        List<String> list = new LinkedList<String>();
        StringTokenizer token = new StringTokenizer(answer, "//");
        String genre1 = "";
        String genre2 = "";
        String duration = "";
        String age = "";
        MovieNode current = overallRoot;
        while (token.hasMoreElements()) {
            genre1 = token.nextToken();
            genre2 = token.nextToken();
            duration = token.nextToken();
            age = token.nextToken();
        }
        while (current.next != null) {
            Movie movie = current.data;
            String durations = "short";
            if (movie.getMinutes() >= 90) {
                durations = "long";
            }
            String ages = "new";
            if (movie.getYear() <= 2000) {
                ages = "old";
            }
            if ((movie.getGenre().equalsIgnoreCase(genre1) || movie.getGenre().equalsIgnoreCase(genre2)) &&
                    durations.equalsIgnoreCase(duration) && ages.equalsIgnoreCase(age)) {
                list.add(movie.getTitle());
            }
            current = current.next;
        }
        return list;
    }
    public List<Movie> findRecommendation(Movie movie) {
        Map<Movie, Integer> results = new HashMap<>();
        MovieNode current = overallRoot;
        while (current.next != null) {
            Movie temp = current.data;
            int connections = 0;
            if (temp.getGenre().equals(movie.getGenre())) {
                connections++;
            }

            if (temp.getYear() == movie.getYear()) {
                connections++;
            }

            if (temp.getMinutes() == movie.getMinutes()) {
                connections++;
            }

            results.put(temp, connections);
            current = current.next;
        }
        List<Movie> recommendations = new ArrayList<>();

        // Sort the movies based on the number of connections
        List<Map.Entry<Movie, Integer>> sortedResults = new ArrayList<>(results.entrySet());
        sortedResults.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

        // Add the movies with the highest connections to the recommendations list
        for (Map.Entry<Movie, Integer> entry : sortedResults) {
            recommendations.add(entry.getKey());
        }

        return recommendations;
    }
}