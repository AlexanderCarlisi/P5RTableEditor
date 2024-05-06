import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public final class GUI {


    public static void startGUI() {
        Window.start();
    }


    private class Window {

        private static JFrame m_instance = new JFrame();

        /** Title of Window */
        private static final String TITLE = "P5R Table Editor";

        /** Starting Width in Pixels */
        private static final int WIDTH = 1280;

        /** Starting Height in Pixels */
        private static final int HEIGHT = 720;


        public static void start() {
            m_instance.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            m_instance.setTitle(TITLE);
            m_instance.setSize(new Dimension(WIDTH, HEIGHT));
            m_instance.setLayout(new BorderLayout());

            m_instance.addComponentListener(new ComponentListener() {
                @Override
                public void componentResized(ComponentEvent e) {
                    for (Component c : m_instance.getComponents()) {
                        c.revalidate();
                    }
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

            MainMenu.open();
            m_instance.setVisible(true);
        }


        public static void refresh() {
            m_instance.revalidate();
        }


        public static void add(Component c) {
            m_instance.add(c);
        }
    }


    private class MainMenu {

        private static JPanel m_instance = new JPanel();

        public static void start() {
            m_instance.setLayout(new BorderLayout());

            JButton personaButton = new JButton("Persona");
            personaButton.setSize(100, 100);
            personaButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    close();
                    PersonaPanel.open();
                }
            });

            m_instance.add(personaButton, BorderLayout.CENTER);

            m_instance.setVisible(false);

            Window.add(personaButton);
        }


        public static void open() {
            m_instance.setVisible(true);
            Window.refresh();
        }

        public static void close() {
            m_instance.setVisible(false);
            Window.refresh();
        }
    }


    private class PersonaPanel {

        /** Overall PersonaPanel, hold all other panels present in this class. */
        private static JPanel m_instance = new JPanel();

        /** Panel for choosing a Persona to edit. */
        private static JPanel m_listPanel = new JPanel();

        /** Panel for Displaying/Editing Information about the currently selected Persona. */
        private static JPanel m_infoPanel = new JPanel();


        public static void start() {
            m_instance.setLayout(new GridLayout(1, 2));

            m_infoPanel.setLayout(new BorderLayout());
            ScrollPane scrollPane = new ScrollPane(ScrollPane.SCROLLBARS_AS_NEEDED);

            for (int i = 0; i < Table.getPersonas().length; i++) {
                JButton button = new JButton(Constants.personaIDtoName[i]);
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Update the infoPanel with the selected Persona's information.
                    }
                });
                scrollPane.add(button);
            }

            m_infoPanel.add(scrollPane, BorderLayout.WEST);

            m_instance.add(m_listPanel);

            m_instance.setVisible(false);

            Window.add(m_instance);
        }


        public static void open() {
            m_instance.setVisible(true);
            Window.refresh();
        }
    }
}
