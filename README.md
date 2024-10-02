This App is for Persona 5 Royal (PC) Modding.

The purpose of this app is to be a more user-friendly way of editing Tables.
The current method to edit tables requires shifting through a sea of 464 personas with no names in 010 editor.
I can only imagine how intimidating it is for someone who wants to Mod P5R but saw 010 editor, so I'm making this App.

Current Features:
  - Uses 2 Tables | Persona.TBL, UNIT.TBL
  - Edit Registry Persona : Stats, StatWeights, Arcana, BitFlags, SkillInheritance, Skills/Trait, and Affinities.
  - Searchable Catalogue of Registry Persona, you can search via Index or Name.
  - Searchable Catalogue of Skills and Traits, Index and Name as well

References:
  - Names and Indexs for Persona : https://amicitia.miraheze.org/wiki/Persona_5_Royal/Personas
  - This whole project wouldn't have been possible without this : https://github.com/tge-was-taken/010-Editor-Templates

TODO: (in order of priority)
  - Enemy Editing : Segments 0 and 1 of Unit.TBL
    - [x] Read from Seg 0
    - [x] Read from Seg 1
    - [x] Properly initialize Enemy instances
    - [x] General Tab
    - [x] Skills Tab 
    - [X] Affinities Tab
    - [X] Write EnemyData to Unit Table
    - [x] Enemy Drops Tab
    - [X] Mass Editing of Enemy Data
    - [X] Code Cleanup, good enough for now

  - Skill Editing : Segments 1 and 2 of Skill.TBL
  - Trait Editing : Segment 4 of Skill.TBL
  - ITEM TBL
  - NAME TBL
    - The more I look into Name.TBL the less I want to do it
  - More to come...