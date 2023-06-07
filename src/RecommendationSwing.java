import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

public class RecommendationSwing extends JFrame {
    private DefaultListModel<String> movieListModel;
    private JList<String> movieList;
    private JTextField searchField;
    private JButton searchButton;
    private JButton exploreButton;
    private JButton filterButton;
    private JComboBox<String> filterComboBox;
    private RecommenderSystem data;
    private MovieDatabase movieData;
    private SurveyNode rootNode;
    private SurveyTree tree;

    public RecommendationSwing() throws FileNotFoundException {
        data = new RecommenderSystem();
        initializeSurveyTree();
        setTitle("Movie Recommender");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(800, 600));

        // Set Nimbus Look and Feel
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // Create GUI components
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        JPanel searchPanel = new JPanel();
        searchField = new JTextField(20);
        searchButton = new JButton("Search");
        exploreButton = new JButton("Explore");
        filterButton = new JButton("Filter");
        filterComboBox = new JComboBox<>(new String[]{"All", "Action", "Comedy", "Drama", "Thriller", "Sci-Fi"});
        movieListModel = new DefaultListModel<>();
        movieData = new MovieDatabase();
        movieList = new JList<>(movieListModel);
        tree = new SurveyTree("survey.txt");

        // Set the search button action
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                searchMovies(searchField.getText());
               // searchMoviesByGenre(searchField.getText());
            }
        });

        // Set the explore button action
        exploreButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showSurveyPage();
                initializeSurveyTree();
            }
        });

        // Set the filter button action
        filterButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Add action for filter button
                searchPanel.remove(filterButton);
                searchPanel.add(filterComboBox, BorderLayout.WEST);
                searchPanel.revalidate();
                searchPanel.repaint();
            }
        });

        filterComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String genre = filterComboBox.getSelectedItem().toString();
                searchMoviesByGenre(genre);
            }
        });

        // Set layout for the searchPanel
        searchPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        // Add components to the search panel
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchPanel.add(filterButton);
        searchPanel.add(exploreButton);

        // Set the background color for the searchPanel
        searchPanel.setBackground(new Color(230, 230, 230));

        // Set the font and background color for the movieList
        movieList.setFont(new Font("Arial", Font.PLAIN, 14));
        movieList.setBackground(Color.white);

        // Add components to the main panel
        panel.add(searchPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(movieList), BorderLayout.CENTER);

        // Set the main panel as the content pane
        setContentPane(panel);
        pack();
        setLocationRelativeTo(null);
        movieListModel.addAll(data.getDatabase().keySet());

        // Initialize the survey tree
        //initializeSurveyTree();

        // Add mouse listener to the movie list
        movieList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int index = movieList.locationToIndex(evt.getPoint());
                if (index >= 0) {
                    String movieName = movieListModel.get(index);
                    displayMovieDetails(movieName);
                }
            }
        });

        setVisible(true);
    }


    private void searchMovies(String query) {
        // Perform a search for movies based on the query
        movieListModel.clear();
        movieListModel.addAll(data.search(query));
    }
    private void searchMoviesByGenre(String query) {
        movieListModel.clear();
        //data.sortByGenre(query);
        movieListModel.addAll(data.searchByGenre(query));
    }

    private void showSurveyPage() {
        // Create a new JFrame or dialog to display the survey page
        JFrame surveyFrame = new JFrame("Survey");
        surveyFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        surveyFrame.setPreferredSize(new Dimension(400, 400));
    
        // Create GUI components for the survey page
        JPanel surveyPanel = new JPanel(new BorderLayout());
    
        // Create a label to display the survey question
        JLabel questionLabel = new JLabel();
        questionLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        questionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        surveyPanel.add(questionLabel, BorderLayout.NORTH);
    
        // Create a panel to hold the answer buttons
        JPanel answerPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        surveyPanel.add(answerPanel, BorderLayout.CENTER);
    
        // Create the initial question and start the survey
        SurveyNode currentNode = rootNode;
        traverseSurvey(currentNode, questionLabel, answerPanel, surveyFrame);
    
        surveyFrame.setContentPane(surveyPanel);
        surveyFrame.pack();
        surveyFrame.setLocationRelativeTo(null);
        surveyFrame.setVisible(true);
    }
    
    private void traverseSurvey(SurveyNode node, JLabel questionLabel, JPanel answerPanel, JFrame surveyFrame) {
        questionLabel.setText(node.getQuestion());
    
        answerPanel.removeAll();
    
        if (node.isLeaf()) {
            // Display the text box for user input
            JTextField textField = new JTextField();
            textField.setPreferredSize(new Dimension(200, 30));
            answerPanel.add(textField);

            JButton submitButton = new JButton("Submit");
            submitButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String userResponse = textField.getText();
                    // Process user response (e.g., save it, perform an action, etc.)
                    System.out.println("User response: " + userResponse);
                    surveyFrame.dispose(); // Close the survey frame
                }
            });
            answerPanel.add(submitButton);
        } else {
            // Display the answer buttons for the current question
            JButton yesButton = new JButton("Yes");
            yesButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // Get the next node based on "Yes" answer
                    SurveyNode nextNode = node.getLeftNode();
                    traverseSurvey(nextNode, questionLabel, answerPanel, surveyFrame);
                }
            });
            answerPanel.add(yesButton);

            JButton noButton = new JButton("No");
            noButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // Get the next node based on "No" answer
                    SurveyNode nextNode = node.getRightNode();
                    traverseSurvey(nextNode, questionLabel, answerPanel, surveyFrame);
                }
            });
            answerPanel.add(noButton);
        }

        surveyFrame.revalidate();
        surveyFrame.repaint();
    }
    private void initializeSurveyTree() {
        try {
            String filePath = "survey.txt"; // Replace with the actual file path
            tree = new SurveyTree(filePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    private void displayMovieDetails(String movieName) {
        // Create a new JFrame or dialog to display the movie details
        
        JFrame movieFrame = new JFrame(movieName);
        Movie movie = movieData.getDatabase().get(movieName);
        movieFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        movieFrame.setPreferredSize(new Dimension(600, 600));

        
        // Create a text area to display the movie details
        JTextArea detailsTextArea = new JTextArea();
        detailsTextArea.setFont(new Font("Arial", Font.PLAIN, 14));
        detailsTextArea.setEditable(false);

        detailsTextArea.setText("Title: " + movie.getTitle() + "\n");
        detailsTextArea.append("Description: " + movie.getDescription() + "\n");
        detailsTextArea.append("Released date: " + movie.getYear() + "\n");
        detailsTextArea.append("Genre: " + movie.getGenre() + "\n");
        detailsTextArea.append("Duration: " + movie.getMinutes() + " minutes\n");
        
        // Add the text area to the movie frame
        movieFrame.getContentPane().add(new JScrollPane(detailsTextArea));
        movieFrame.pack();
        movieFrame.setLocationRelativeTo(null);
        movieFrame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    new RecommendationSwing();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}