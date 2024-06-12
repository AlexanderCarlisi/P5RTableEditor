import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public final class GUI {


    public static void startGUI() {
        Window.start();
    }


    private class Window {

        /** Starting Width in Pixels */
        public static final int WIDTH = 1280;

        /** Starting Height in Pixels */
        public static final int HEIGHT = 720;

        private final static JFrame m_instance = new JFrame();

        /** Title of Window */
        private static final String TITLE = "P5R Table Editor";


        public static void start() {
            m_instance.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            m_instance.setTitle(TITLE);
            m_instance.setSize(new Dimension(WIDTH, HEIGHT));
            m_instance.setLayout(new BorderLayout());

            m_instance.addComponentListener(new ComponentListener() {
                @Override
                public void componentResized(ComponentEvent e) {
                    MainMenu.resize();
                    PersonaPanel.resize();
                }

                @Override
                public void componentMoved(ComponentEvent e) {}

                @Override
                public void componentShown(ComponentEvent e) {}

                @Override
                public void componentHidden(ComponentEvent e) {}
            });

            MainMenu.start();
            PersonaPanel.start();

            // MainMenu.open();
            m_instance.setVisible(true);

            MainMenu.open();
        }


        public static void refresh() {
            m_instance.revalidate();
            m_instance.repaint();
        }


        public static void add(Component c, String borderLayoutPosition) {
            m_instance.add(c, borderLayoutPosition);
            refresh();
        }
    }


    private class MainMenu {

        private final static JPanel m_instance = new JPanel();

        public static void start() {
            m_instance.setLayout(new BoxLayout(m_instance, BoxLayout.X_AXIS));

            JButton personaButton = new JButton("Persona");
            personaButton.setPreferredSize(new Dimension(Window.WIDTH / 10, Window.HEIGHT / 10));
            personaButton.setMaximumSize(personaButton.getPreferredSize());
            personaButton.addActionListener((ActionEvent e) -> {
                close();
                PersonaPanel.open();
            });
            m_instance.add(Box.createHorizontalGlue());
            m_instance.add(personaButton);
            m_instance.add(Box.createHorizontalGlue());
            Window.add(m_instance, BorderLayout.CENTER);

            close();
        }


        public static void open() {
            for (Component c : m_instance.getComponents()) {
                c.setVisible(true);
            }
            m_instance.setVisible(true);
            Window.refresh();
        }

        public static void close() {
            for (Component c : m_instance.getComponents()) {
                c.setVisible(false);
            }
            m_instance.setVisible(false);
            Window.refresh();
        }

        public static void resize() {
            m_instance.setSize(Window.WIDTH, Window.HEIGHT);
        }
    }


    private class PersonaPanel {

        /** Overall PersonaPanel, hold all other panels present in this class. */
        private final static JPanel m_instance = new JPanel();

        /** Panel for choosing a Persona to edit. */
        private final static JPanel m_listPanel = new JPanel();

        /** Panel containing all the Buttons to access Persona. */
        private final static JPanel m_buttonPanel = new JPanel();

        /** ScrollPane for the Persona Buttons. */
        private final static JScrollPane m_scrollPane = new JScrollPane(m_buttonPanel);

        /** Panel for Displaying/Editing Information about the currently selected Persona. */
        private final static JPanel m_infoPanel = new JPanel();
        
        /** Panel for the first column of the InfoPanel. */
        private final static JPanel m_col1Panel = new JPanel();
        private final static Font m_font = new Font(null, Font.PLAIN, 15);
        private final static JLabel m_nameLabel = new JLabel();
        private final static JComboBox<Enums.Arcana> m_arcanaComboBox = new JComboBox<>(Enums.Arcana.values());
        private final static JTextField m_levelField = new JTextField();
        private final static JTextField[] m_statFields = new JTextField[5];

        /** Panel for the second column of the InfoPanel. */
        private final static JPanel m_col2Panel = new JPanel();

        // private static JComboBox<Enums.> resistancesComboBox = new JComboBox<>();
        private final static JToggleButton[] m_bitFlagToggles = new JToggleButton[10];


        public static void start() {
            m_instance.setLayout(new GridLayout(1, 2));

            // List Panel
            m_listPanel.setLayout(new BorderLayout());
            m_buttonPanel.setLayout(new BoxLayout(m_buttonPanel, BoxLayout.Y_AXIS));
            m_scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

            for (int i = 0; i < Table.getPersonas().length; i++) {
                JButton button = new JButton(Constants.personaIDtoName[i]);
                int index = i;
                button.addActionListener((ActionEvent e) -> {
                    // Update the infoPanel with the selected Persona's information.
                    updateInfo(index);
                });
                m_buttonPanel.add(button);
            }

            m_listPanel.add(m_scrollPane);
            m_instance.add(m_listPanel);

            // Info Panel
            // Split into 3 Columns
            // 1: name/arcana/level/stats 2: resistances 3: skills 4: bitflags
            m_infoPanel.setLayout(new GridLayout(1, 4));

            // Column 1 - General info
            m_col1Panel.setLayout(new BoxLayout(m_col1Panel, BoxLayout.Y_AXIS));

            m_nameLabel.setText("Name | *Nothing Selected");
            m_nameLabel.setFont(m_font);

            m_arcanaComboBox.setPreferredSize(new Dimension(Window.WIDTH / 7, Window.HEIGHT / 20));
            m_arcanaComboBox.setMaximumSize(m_arcanaComboBox.getPreferredSize());
            m_arcanaComboBox.addActionListener((ActionEvent e) -> {
                // Update the selected Persona's Arcana.
            });

            JPanel statPanel = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();

            Dimension statLabelSize = new Dimension(Window.WIDTH / 15, Window.HEIGHT / 40);
            Dimension statFieldSize = new Dimension(Window.WIDTH / 30, Window.HEIGHT / 40);

            JLabel levelLabel = new JLabel("Level:");
            levelLabel.setFont(m_font);
            levelLabel.setPreferredSize(statFieldSize);
            levelLabel.setMaximumSize(statFieldSize);

            JLabel[] statLabels = {
                new JLabel("Strength:"),
                new JLabel("Magic:"),
                new JLabel("Endurance:"),
                new JLabel("Agility:"),
                new JLabel("Luck:")
            };

            for (int i = 0; i < statLabels.length; i++) {
                m_statFields[i] = new JTextField();

                statLabels[i].setFont(m_font);
                statLabels[i].setPreferredSize(statLabelSize);
                statLabels[i].setMaximumSize(statLabelSize);

                m_statFields[i].setPreferredSize(statFieldSize);
                m_statFields[i].setMaximumSize(statFieldSize);

                gbc.gridx = 0;
                gbc.gridy = i;
                statPanel.add(statLabels[i], gbc);

                gbc.gridx = 1;
                statPanel.add(m_statFields[i], gbc);
            }

            JPanel nameArcanaWrapper = new JPanel();
            nameArcanaWrapper.setLayout(new BoxLayout(nameArcanaWrapper, BoxLayout.Y_AXIS));
            nameArcanaWrapper.add(m_nameLabel);
            nameArcanaWrapper.add(m_arcanaComboBox);

            m_col1Panel.add(nameArcanaWrapper);
            m_col1Panel.add(statPanel);

            // Column 2 - Resistances
            m_col2Panel.add(new JLabel("Resistances | Unimplemented"));

            // Column 3 - Skills
            m_col2Panel.add(new JLabel("Skills | Unimplemented"));

            // Column 4 - Bitflags
            m_col2Panel.add(new JLabel("Bitflags | Unimplemented"));

            m_infoPanel.add(m_col1Panel);   
            m_infoPanel.add(m_col2Panel);
            m_instance.add(m_infoPanel);         
            Window.add(m_instance, BorderLayout.CENTER);

            close();
        }


        public static void open() {
            for (Component c : m_instance.getComponents()) {
                c.setVisible(true);
            }
            m_instance.setVisible(true);
            Window.refresh();
        }

        public static void close() {
            for (Component c : m_instance.getComponents()) {
                c.setVisible(false);
            }
            m_instance.setVisible(false);
            Window.refresh();
        }

        public static void resize() {
            m_instance.setSize(Window.WIDTH, Window.HEIGHT);
            
        }

        /**
         * Updates the InfoPanel with the information of the Persona at the given index.
         * @param index
         */
        public static void updateInfo(int index) {
            // Update the name, arcana, level, and stats of the Persona.
            Persona persona = Table.getPersonas()[index];
            m_nameLabel.setText(Constants.personaIDtoName[index]);
            m_arcanaComboBox.setSelectedItem(persona.getArcana());
            m_levelField.setText(Integer.toString(persona.getLevel()));
            for (int i = 0; i < 5; i++) {
                m_statFields[i].setText(Integer.toString(persona.getStats()[i]));
            }
        }
    }
}
