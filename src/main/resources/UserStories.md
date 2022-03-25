
#Brukerhistorier:

###Prosjektstruktur:

- Som en utvikler ønsker jeg en bedre prosjektstruktur slik at programmet blir lettere å utvide.

- Som spiller ønsker jeg at utvikler skal ha god prosjektstruktur slik at spillet kan utvikles kjappere og bli større.

- Akseptansekriterier:

- Prosjektet har samme funksjonalitet som før

- Prosjektstrukturen følger MVC-prinsippet

- Arbeidsoppgaver:

- Opprette en modell for å holde på spill logikken

- Flytte funksjonalitet som ikke har med view å gjøre inn i modellen

###Spillerteksturer:

- Som spiller ønsker jeg å ha forskjellige teksturer for forskjellige tilstander spilleren befinner seg i fordi dette vil gi en mer &quot;levende&quot; spillkarakter som gjør at jeg kan leve meg mer inn i spillet.

- Akseptansekriterier:

- Forskjellige teksturer for de forskjellige tilstandene en spiller kan befinne seg i (Stå, gå, hoppe og falle).

- Teksturene til spillerne må peke i samme retning som spilleren beveger seg i.

- Arbeidsoppgaver:

- Lage/sette sammen en png-fil (64x64 px) med flere teksturer etter hverandre (alle vendt i samme retning)

- Legge til funksjonalitet for å finne hvilken tilstand spilleren er i, samt retning spilleren beveger seg i.

- Legge til funksjonalitet for å velge rett tekstur til rett tilstand.

###Større spillbrett:

- Som utvikler ønsker jeg et større spillbrett for å teste mer funksjonalitet, og utvikle meir interessante spillopplevelser

- Akseptansekriterier:

- Det nye brettet inneholder poeng gjenstander, mulighet for fiender og død

- Brettet lastes inn og vises på skjermen

- Arbeidsoppgaver:

- Lag nytt brett med ovennevnte funksjonalitet

- Last inn brett i TileMapHelper. Edit: Finn problem som hindrer lasting av brett

- Exception handling for lasting av nytt brett

###Poeng og poeng gjenstander (Spiller har poeng og interagerer med poeng gjenstander):

- Som spiller ønsker jeg et poengsystem slik at jeg føler meg belønnet for å spille spillet bra

- Akseptansekriterier:

- Spillmodellen holder styr på poeng

- En poengteller viser antall poeng på skjermen

- Modellen har en poeng gjenstand

- Poeng gjenstanden vises på skjermen

- Poenggjenstanden forsvinner ved interaksjon og poengtelleren oppdateres

- Arbeidsoppgaver:

- Legg til variabel i modellen som holder på antall poeng

- (per spiller?)

- Vis antall poeng på skjermen

- Lag/finn en poeng gjenstand og legg til i brettet (sammenheng med &quot;større spillbrett&quot; historie)

- Legg til funksjonalitet for å sjekke kontakt mellom spiller og poeng gjenstand

- Objektet må ha en body med en gitt contact type

- Ved kontakt, fjern objektet fra brettet og legg til poeng i modellen

- Pass på at poengtelleren oppdateres

###Fiender (Vise fiender/monstre; de skal interagere med terreng og spiller):

- Som spiller ønsker jeg fiender/monstre som jeg må unngå for å få litt spenning/utfordring i spillet

- Akseptansekriterier:

- Spillmodellen har et fiende objekt

- Kan være stasjonært, utvide med bevegelse etter hvert

- Fienden vises på skjermen

- Fienden har en tekstur og en body

- Spiller kan ikke gå gjennom fiende

- Arbeidsoppgaver:

- Opprette et fiende objekt i modellen

- Legg til fiende i tilemap

- Pass på at fiende lastes inn

- Legg til kontakt mellom spiller og fiende

###Spillerdød (Spiller kan dø (ved kontakt med fiender, eller ved å falle utfor skjermen)):

- Som spiller ønsker jeg en fare for at spillet avsluttes slik at spillet blir mer interessant

- Akseptansekriterier:

- Spiller objektet har helse

- Ved kontakt med fiende mister spilleren helse

- Ved kontakt med death plane mister spilleren all helse

- Ved null (0) helse dør spilleren, kan ikke lenger bevege seg

- Arbeidsoppgaver:

- Legg til variabel for helse i spiller objektet

- Sjekk kontakt mellom spiller og fiende/death plane, og oppdater spiller helse

- Sjekk om spiller helse er mindre eller lik 0, fjern mulighet for bevegelse

###Mål for spillbrett (enten et sted, en mengde poeng, drepe alle fiender e.l.):

- Som spiller ønsker jeg et mål i spillet for å ha noe å jobbe mot når jeg spiller
    - Akseptansekriterier:
        - Modellen har en klasse Goal
        - Ved kontakt med Goal-objektet skal modellen sette status på levelet som fullført
        - Goal-objektet vises på skjermen med egen tekstur
    - Arbeidsoppgaver:
        - Lag et &quot;Goal&quot; objekt
        - legg Goal objektet inn i brettet
        - sett brettet inn i en &quot;finished state&quot; når en spiller treffer goal objektet

###Nytt spillbrett når forrige er ferdig:

- Som programmerer vil jeg ha muligheten til å legge til flere spillbrett for at spillet skal bli lengre.
- Som spiller vil muligheten å bytte mellom flere spillbrett føre til meir interessante og varierte spillopplevelser.
    - Akseptansekriterier:
        - Ved kontakt med målobjekt skal nytt spillbrett lastes inn
        - Ved død skal det nåværende spillbrettet lastes inn på nytt
        - Alle objektene kan interageres med på vanlig måte etter lasting
            - Objekter skal &quot;resetes&quot; til sin originale tilstand
        - Score blir satt til null ved død
    - Arbeidsoppgaver:
        - Fjern alle objekter fra brettet.
        - Last inn ett nytt brett.
        - Last inn objekter fra det nye brettet.
        - Lag metode som gjør dette når man ønsker å laste nytt brett
        - Bruk ny metode til å laste neste level når brettet er i &quot;finished state&quot; og samme level dersom alle spillere dør

###Bedre kamera:

- Som spillere ønsker vi et kamera som følger alle spillere slik at alle som spiller kan se hva som skjer på skjermen fordi da kan alle spillerne spille sammen
    - Akseptansekriterier:
        - Kameraet følger alle spillerne
        - Alle spillerne kan sees hele tiden
        - Ingen exception blir kastet dersom alle spillere er døde
    - Arbeidsoppgaver:
        - Opprett en klasse for ett custom kamera
        - Lag metode(r) for å regne ut kameraposisjon
        - Lag sjekk og håndter kameraposisjon når spillerne er døde

###Start-skjerm ved oppstart / game over

- Som utvikler ønsker jeg muligheten for å sette en start- og game over skjerm slik at jeg har mer kontroll over spillerens opplevelse og kan til en viss grad kontrollere tempoet i spillet
    - Akseptansekriterier:
        - Ved oppstart skal en StartScreen/MenuScreen vises
        - Når spilleren trykker på en tast starter spillet ved å bytte til GameScreen
            - Første spillbrett skal lastes inn ved bytte til GameScreen
        - Når spillerne er døde skal GameoverScreen vises
            - Når spilleren trykker på en tast byttes spillet til GameScreen og levelet lastes inn på nytt
    - Arbeidsoppgaver:
        - Opprett klasser Start/MenuScreen og GameoverScreen som implementerer Screen
        - Render en fornuftig melding på de nye skjermene
        - La Controller sjekke etter input som endrer skjerm hvis spillet er på start- eller sluttskjermen
        - La Boot sette skjermen til Start/MenuScreen
        - Bytt til GameoverScreen ved spillerdød
        - La GameModel holde styr på hvilken skjerm som er aktiv (for å bestemme hva som skal skje ved bytting av skjerm)
            - Ved bytting fra GameoverScreen til GameScreen skal samme levelet lastes på nytt
            - Ved bytting fra Start/MenuScreen til GameScreen skal første levelet lastes