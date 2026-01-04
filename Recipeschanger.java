//ADD LIBRARIES THAT WE ARE GOING TO USE
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

//CREATE CLASS RECIPES CHANGER Y JFRAME
public class Recipeschanger extends JFrame {

    //ADD THE GRAPHICS
    private JTextArea inputArea;
    private JTextArea outputArea;
    private JComboBox<String> conditionBox;
    private JButton btnGenerate;

    //CREATE THE PROGRAM
    public Recipeschanger() {
        setTitle("Recipe Changer Program");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // TOP
        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("Select your main health goal:"));
        String[] conditions = {"High Colesterol", "Lose Weight", "Gluten Allergy", "High blood pressure"};
        conditionBox = new JComboBox<>(conditions);
        topPanel.add(conditionBox);
        add(topPanel, BorderLayout.NORTH);

        // CENTER
        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(30, 20, 50, 20));

        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.add(new JLabel("   Paste original recipe ingredients:"), BorderLayout.NORTH);
        inputArea = new JTextArea("Example:\n2 cup sour cream\n7 eggs\n1 cup sugar");
        leftPanel.add(new JScrollPane(inputArea), BorderLayout.CENTER);

        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.add(new JLabel("   Healthier Suggestions:"), BorderLayout.NORTH);
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setForeground(new Color(255, 9, 9));
        rightPanel.add(new JScrollPane(outputArea), BorderLayout.CENTER);

        centerPanel.add(leftPanel);
        centerPanel.add(rightPanel);
        add(centerPanel, BorderLayout.CENTER);

        // BOTTOM
        JPanel bottomPanel = new JPanel();
        btnGenerate = new JButton("Create New Recipe");
        bottomPanel.add(btnGenerate);
        add(bottomPanel, BorderLayout.SOUTH);

        btnGenerate.addActionListener(e -> processRecipe());
    }
    //CREATE ALL THE CONDITIONS TO MAKE THE CHANGES IN THE RECIPES
    private void processRecipe() {
        String originalText = inputArea.getText();
        String selectedCondition = (String) conditionBox.getSelectedItem();

        StringBuilder result = new StringBuilder();
        result.append("RESULTS FOR: ").append(selectedCondition.toUpperCase()).append("\n\n");

        String[] lines = originalText.split("\n");

        for (String line : lines) {
            String lowerLine = line.toLowerCase();
            String newLine = line;

            // -------- EGGS (Lose weight o colesterol)
            if ((selectedCondition.equals("High Colesterol") || selectedCondition.equals("Lose Weight"))
                    && lowerLine.contains("egg") && !lowerLine.contains("white")) {

                String numeric = line.replaceAll("[^0-9]", "");
                if (!numeric.isEmpty()) {
                    int q = Integer.parseInt(numeric);
                    newLine = (q * 2) + " egg whites (Substitute for " + line + ")";
                } else {
                    newLine = line + " -> Use egg whites instead";
                }
            }

            // -------- High Cholesterol
            if (selectedCondition.equals("High Colesterol")) {
                if (lowerLine.contains("sour cream")) newLine = "1 cup Greek yogurt (Substitute for sour cream)";
                else if (lowerLine.contains("milk")) newLine = "1 cup soy or skim milk (Substitute for milk)";
                else if (lowerLine.contains("butter")) newLine = "1 cup olive oil or low-fat yogurt (Substitute for butter)";
            }

            //  Lose Weight
            if (selectedCondition.equals("Lose Weight")) {
                if (lowerLine.contains("sugar")) newLine = "1/2 cup stevia or agave";
                else if (lowerLine.contains("oil")) newLine = "1/4 cup applesauce";
                else if (lowerLine.contains("mayonnaise")) newLine = "Greek yogurt instead of mayo";
            }

            // Gluten Allergy
            if (selectedCondition.equals("Gluten Allergy")) {
                if (lowerLine.contains("flour")) newLine = "Rice flour or oat flour";
                else if (lowerLine.contains("bread")) newLine = "Gluten-free bread or corn tortillas";
            }

            // High Blood Pressure
            if (selectedCondition.equals("High blood pressure")) {
                if (lowerLine.contains("salt")) newLine = "Use herbs, lemon, or garlic powder";
                if (lowerLine.contains("butter")) newLine = "Olive oil or avocado spread";
                if (lowerLine.contains("cheese")) newLine = "Low-sodium cheese or ricotta";
            }

            result.append(newLine).append("\n");
        }

        result.append("\nDISCLAIMER: Consult a doctor before major dietary changes.");
        outputArea.setText(result.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Recipeschanger frame = new Recipeschanger();
            frame.setVisible(true);
        });
    }
}
