# P5R Table Editor

P5R Table Editor is a tool designed for the PC version of *Persona 5 Royal*, enabling modders to easily edit Table files. This application simplifies the editing process for various game data.

## Supported Editing

Currently, the P5R Table Editor supports editing the following data:

- **PERSONA.TBL**: Persona Registry, Party Member Personas
- **UNIT.TBL**: Party Member Personas, Enemy Data

## References Used

- Names and Indexes for Persona: [Amicitia](https://amicitia.miraheze.org/wiki/Persona_5_Royal/Personas)
- The 010 Editor Templates by tge-was-taken: [GitHub Repository](https://github.com/tge-was-taken/010-Editor-Templates)

## How to Use

The release's ZIP folder comes with the following items:

- `inputs` directory
- `outputs` directory
- `originals` directory
- `P5RTableEditor-version#.exe` file
- `README.txt` - instructions on how to use the app
- `application.log` - log file for errors

### Steps to Get Started:

1. Place the tables you want to edit into the `inputs` directory.
2. Run the application (`editorApplication.exe`).
3. The app will generate output files based on your input files; your original input files will remain unmodified.

### Features:

- Reset output files to their corresponding input files and originals directory files.
- User-friendly interface with labels and prompts for easy navigation.
- Indexed and named enemies in the Registry and Enemy Editors for efficient searching by Index or Name.
- Mass edit enemy stats by selecting a stat and applying a multiplier.

### Saving Changes:

When you click "Return to Main Menu," the app will prompt you to save your changes. After saving, your outputs directory will contain the new tables. Replace your mod's tables with those in the output folder to begin testing your mod!

## Troubleshooting

If you notice a value resetting to zero after saving or changing tabs, this is due to the value being invalid. 

For further assistance or suggestions, feel free to reach out!

---

Thank you for using P5R Table Editor. Happy modding!
