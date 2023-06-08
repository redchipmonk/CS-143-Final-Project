import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.util.List;
/**
 * This class represents the operations and handling of the GUI. It imports the logic from RecommenderSystem.java
 *
 * @author Eric Im, Alvin Le
 */
public class RecommendationSwing extends JFrame {
    private DefaultListModel<String> movieListModel;
    private JList<String> movieList;
    private JTextField searchField;
    private JButton searchButton;
    private JButton exploreButton;
    private JButton filterButton;
    private JButton addButton;
    private JButton removeButton;
    private JComboBox<String> filterComboBox;
    private RecommenderSystem data;
    private SurveyTree tree;
    /**
     * Default constructor to initialize the GUI.
     * @throws FileNotFoundException if file to read from is not found
     */
    public RecommendationSwing() throws FileNotFoundException {
        data = new RecommenderSystem("movies.txt");
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
        filterComboBox = new JComboBox<>(new String[]{"All", "Action", "Comedy", "Drama", "Adventure", "Crime"});
        addButton = new JButton("Add");
        removeButton = new JButton("Remove");
        movieListModel = new DefaultListModel<>();
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
                List<String> list = data.filter(genre);
                movieListModel.clear();
                for (int i = 0; i < list.size(); i++) {
                    if (!movieListModel.contains(list.get(i)) && list.get(i).contains(searchField.getText())) {
                        searchMovies(searchField.getText());
                        movieListModel.addElement(list.get(i));
                    }
                }
            }
        });
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showAddPage();
            }
        });
        removeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showRemovePage();
            }
        });
        // Set layout for the searchPanel
        searchPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        // Add components to the search panel
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchPanel.add(filterButton);
        searchPanel.add(exploreButton);
        searchPanel.add(addButton);
        searchPanel.add(removeButton);
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
        movieListModel.addAll(data.getTitles());
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
    /**
     * Resets the display with movies that matches the search.
     * @param query String to search for within movie title
     */
    private void searchMovies(String query) {
        // Perform a search for movies based on the query
        movieListModel.clear();
        movieListModel.addAll(data.search(query));
    }
    /**
     * Displays the add form to add a movie to the list
     */
    private void showAddPage() {
        // Create a new JFrame or dialog to display the survey page
        JFrame addFrame = new JFrame("Add A Movie");
        addFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addFrame.setPreferredSize(new Dimension(400, 400));
        // Create GUI components for the survey page
        JPanel addPanel = new JPanel(new BorderLayout());
        addPanel.setLayout(new GridLayout(7, 2));
        addPanel.add(new JLabel("Title:"));
        JTextField titleField = new JTextField();
        addPanel.add(titleField);
        // Description label and text field
        addPanel.add(new JLabel("Description:"));
        JTextField descriptionField = new JTextField();
        addPanel.add(descriptionField);
        // Year label and text field
        addPanel.add(new JLabel("Year:"));
        JTextField yearField = new JTextField();
        addPanel.add(yearField);
        // Genre label and text field
        addPanel.add(new JLabel("Genre:"));
        JTextField genreField = new JTextField();
        addPanel.add(genreField);
        // Minutes label and text field
        addPanel.add(new JLabel("Minutes:"));
        JTextField minutesField = new JTextField();
        addPanel.add(minutesField);
        // Create a label to display the survey question
        JLabel AddLabel = new JLabel();
        AddLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        AddLabel.setHorizontalAlignment(SwingConstants.CENTER);
        addPanel.add(AddLabel, BorderLayout.NORTH);
        JButton submitButton = new JButton("SUBMIT");
        addPanel.add(submitButton);
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String title = titleField.getText();
                String description = descriptionField.getText();
                int year = Integer.parseInt(yearField.getText());
                String genre = genreField.getText();
                int minutes = Integer.parseInt(minutesField.getText());

                // Create a new Movie object with the entered details
                Movie movie = new Movie(title, description, year, genre, minutes);

                // Perform any desired actions with the newly created movie
                data.add(movie);
                addFrame.dispose();
                movieListModel.clear();
                movieListModel.addAll(data.getTitles());
            }
        });
        JPanel answerPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        addPanel.add(answerPanel, BorderLayout.CENTER);
        addFrame.setContentPane(addPanel);
        addFrame.pack();
        addFrame.setLocationRelativeTo(null);
        addFrame.setVisible(true);
    }
    /**
     * Displays the remove form to remove a movie from the list.
     */
    private void showRemovePage() {
        // Create a new JFrame or dialog to display the survey page
        JFrame removeFrame = new JFrame("Remove A Movie");
        removeFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        removeFrame.setPreferredSize(new Dimension(400, 400));
        // Create GUI components for the survey page
        JPanel removePanel = new JPanel(new BorderLayout());
        removePanel.setLayout(new GridLayout(6, 2));
        removePanel.add(new JLabel("Title of the movie you would like to remove?"));
        JTextField titleField = new JTextField();
        removePanel.add(titleField);
        JButton submitButton = new JButton("SUBMIT");
        removePanel.add(submitButton);
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String title = titleField.getText();
                data.remove(title);
                removeFrame.dispose();
                movieListModel.clear();
                movieListModel.addAll(data.getTitles());
            }
        });
        removeFrame.setContentPane(removePanel);
        removeFrame.pack();
        removeFrame.setLocationRelativeTo(null);
        removeFrame.setVisible(true);
    }
    /**
     * Displays the survey page to prompt the user to answer the survey.
     */
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
        SurveyNode currentNode = tree.getRootNode();
        traverseSurvey(currentNode, questionLabel, answerPanel, surveyFrame);
        surveyFrame.setContentPane(surveyPanel);
        surveyFrame.pack();
        surveyFrame.setLocationRelativeTo(null);
        surveyFrame.setVisible(true);
    }
    /**
     * Takes the user through the survey and generates recommendations from the survey results.
     * @param node current node to answer
     * @param questionLabel label of question
     * @param answerPanel panel of two choices
     * @param surveyFrame frame of survey
     */
    private void traverseSurvey(SurveyNode node, JLabel questionLabel, JPanel answerPanel, JFrame surveyFrame) {
        questionLabel.setText(node.getQuestion());
        answerPanel.removeAll();
        if (node.isLeaf()) {
            List<String> list = data.generate(node.getQuestion().substring(2));
            //creates a submit button once answer is reached
            JButton submitButton = new JButton("Submit");
            submitButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    surveyFrame.dispose();
                    movieListModel.clear();
                    movieListModel.addAll(list);
                }
            });
            answerPanel.add(submitButton);
        }
        else {
            String[] s = node.getQuestion().substring(2).split("//");
            // Display the answer buttons for the current question
            JButton leftButton = new JButton(s[0]);
            leftButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // Get the next node based on "Yes" answer
                    traverseSurvey(node.left, questionLabel, answerPanel, surveyFrame);
                }
            });
            answerPanel.add(leftButton);
            JButton rightButton = new JButton(s[1]);
            rightButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // Get the next node based on "No" answer
                    traverseSurvey(node.right, questionLabel, answerPanel, surveyFrame);
                }
            });
            answerPanel.add(rightButton);
        }
        surveyFrame.revalidate();
        surveyFrame.repaint();
    }
    /**
     * Initializes the survey tree to read from file.
     */
    private void initializeSurveyTree() {
        try {
            tree = new SurveyTree("survey.txt");
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    /**
     * Displays the details of a movie object.
     * @param movieName title to search for
     */
    private void displayMovieDetails(String movieName) {
        // Create a new JFrame or dialog to display the movie details
        JFrame movieFrame = new JFrame(movieName);
        Movie movie = data.find(movieName).data;
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
        detailsTextArea.append("Duration: " + movie.getMinutes() + " minutes\n\n");
        detailsTextArea.append("Other movies: \n");
        List<Movie> find = data.findRecommendation(movie);
        for (int i = 1; i < 4; i++) {
            detailsTextArea.append(find.get(i).getTitle() + "\n");
        }
        // Add the text area to the movie frame
        movieFrame.getContentPane().add(new JScrollPane(detailsTextArea));
        movieFrame.pack();
        movieFrame.setLocationRelativeTo(null);
        movieFrame.setVisible(true);
    }
    /**
     * Runs the GUI.
     * @param args command line arguments
     */
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