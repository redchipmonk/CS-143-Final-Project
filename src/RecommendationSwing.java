import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RecommendationSwing extends JFrame {
    private DefaultListModel<String> movieListModel;
    private JList<String> movieList;
    private JTextField searchField;
    private JButton searchButton;
    private JButton exploreButton;
    private JButton filterButton;

    public RecommendationSwing() {
        setTitle("Movie Recommender");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(400, 300));

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
        movieListModel = new DefaultListModel<>();
        movieList = new JList<>(movieListModel);

        // Set the search button action
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                searchMovies(searchField.getText());
            }
        });

        // Set the explore button action
        exploreButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Add action for explore button
                JOptionPane.showMessageDialog(RecommendationSwing.this, "Explore button clicked");
            }
        });

        // Set the filter button action
        filterButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Add action for filter button
                JOptionPane.showMessageDialog(RecommendationSwing.this, "Filter button clicked");
            }
        });

        // Set layout for the searchPanel
        searchPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        // Add components to the search panel
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        // Create a panel to hold the searchPanel vertically
        JPanel searchPanelWrapper = new JPanel();
        searchPanelWrapper.setLayout(new BoxLayout(searchPanelWrapper, BoxLayout.Y_AXIS));
        searchPanelWrapper.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        searchPanelWrapper.add(searchPanel);
        searchPanelWrapper.add(Box.createVerticalStrut(10));
        searchPanelWrapper.add(exploreButton);
        searchPanelWrapper.add(Box.createVerticalStrut(10));
        searchPanelWrapper.add(filterButton);

        // Set the background color for the searchPanelWrapper
        searchPanelWrapper.setBackground(new Color(230, 230, 230));

        // Set the font and background color for the movieList
        movieList.setFont(new Font("Arial", Font.PLAIN, 14));
        movieList.setBackground(Color.white);

        // Add components to the main panel
        panel.add(searchPanelWrapper, BorderLayout.NORTH);
        panel.add(new JScrollPane(movieList), BorderLayout.CENTER);

        // Set the main panel as the content pane
        setContentPane(panel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void searchMovies(String query) {
        // Perform a search for movies based on the query
        // Replace this code with your own movie recommendation logic

        // For this example, we'll clear the list and add some dummy search results
        movieListModel.clear();
        movieListModel.addElement("Result 1");
        movieListModel.addElement("Result 2");
        movieListModel.addElement("Result 3");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new RecommendationSwing();
            }
        });
    }
}
