public class App {
    public static void main(String[] args) throws Exception {
        // Table tablePersona = new Table(Constants.Path.kTablePersona);
        // System.out.println("\n\nReading: \n\n\n" + tablePersona.read());
        // Persona persona = tablePersona.readPersona();

        // Persona[] personas = tablePersona.readPersonas();

        // for (int i = 0; i < 10; i++) {
        //     System.out.println(personas[0].getBitFlags()[i]);
        // }
        // System.out.println("\nArcana: " + personas[0].getArcana());
        // System.out.println("\nLevel: " + personas[0].getLevel());
        // for (int i = 0; i < 5; i++) {
        //     System.out.println("\nStat " + i + ": " + personas[0].getStats()[i]);
        // }
        // System.out.println("\nSkill Inheritance: " + personas[0].getSkillInheritance());
        
        Table.startPersonaStream();
        // Table.readPersonas();

        GUI.startGUI();
    }
}
