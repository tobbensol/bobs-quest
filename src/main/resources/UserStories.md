# Brukerhistorier:

### Prosjektstruktur:

- Som en utvikler ønsker jeg en bedre prosjektstruktur slik at programmet blir lettere å utvide.

- Som spiller ønsker jeg at utvikler skal ha god prosjektstruktur slik at spillet kan utvikles kjappere og bli større.

    - Akseptansekriterier:

        - Prosjektet har samme funksjonalitet som før

        - Prosjektstrukturen følger MVC-prinsippet

    - Arbeidsoppgaver:

        - Opprette en modell for å holde på spill logikken

        - Flytte funksjonalitet som ikke har med view å gjøre inn i modellen

### Spillerteksturer:

- Som spiller ønsker jeg å ha forskjellige teksturer for forskjellige tilstander spilleren befinner seg i fordi dette
  vil gi en mer &quot;levende&quot; spillkarakter som gjør at jeg kan leve meg mer inn i spillet.

    - Akseptansekriterier:

        - Forskjellige teksturer for de forskjellige tilstandene en spiller kan befinne seg i (Stå, gå, hoppe og falle).

        - Teksturene til spillerne må peke i samme retning som spilleren beveger seg i.

    - Arbeidsoppgaver:

        - Lage/sette sammen en png-fil (64x64 px) med flere teksturer etter hverandre (alle vendt i samme retning)

        - Legge til funksjonalitet for å finne hvilken tilstand spilleren er i, samt retning spilleren beveger seg i.

        - Legge til funksjonalitet for å velge rett tekstur til rett tilstand.

### Større spillbrett:

- Som utvikler ønsker jeg et større spillbrett for å teste mer funksjonalitet, og utvikle meir interessante
  spillopplevelser

    - Akseptansekriterier:

        - Det nye brettet inneholder poeng gjenstander, mulighet for fiender og død

        - Brettet lastes inn og vises på skjermen

    - Arbeidsoppgaver:

        - Lag nytt brett med ovennevnte funksjonalitet

        - Last inn brett i TileMapHelper. Edit: Finn problem som hindrer lasting av brett

        - Exception handling for lasting av nytt brett

### Poeng og poeng gjenstander (Spiller har poeng og interagerer med poeng gjenstander):

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

### Fiender (Vise fiender/monstre; de skal interagere med terreng og spiller):

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

### Spillerdød (Spiller kan dø (ved kontakt med fiender, eller ved å falle utfor skjermen)):

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

### Mål for spillbrett (enten et sted, en mengde poeng, drepe alle fiender e.l.):

- Som spiller ønsker jeg et mål i spillet for å ha noe å jobbe mot når jeg spiller
    - Akseptansekriterier:
        - Modellen har en klasse Goal
        - Ved kontakt med Goal-objektet skal modellen sette status på levelet som fullført
        - Goal-objektet vises på skjermen med egen tekstur
    - Arbeidsoppgaver:
        - Lag et &quot;Goal&quot; objekt
        - legg Goal objektet inn i brettet
        - sett brettet inn i en &quot;finished state&quot; når en spiller treffer goal objektet

### Nytt spillbrett når forrige er ferdig:

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
        - Bruk ny metode til å laste neste level når brettet er i &quot;finished state&quot; og samme level dersom alle
          spillere dør

### Bedre kamera:

- Som spillere ønsker vi et kamera som følger alle spillere slik at alle som spiller kan se hva som skjer på skjermen
  fordi da kan alle spillerne spille sammen.
    - Akseptansekriterier:
        - Kameraet følger de ytterste spillerne
        - Alle spillerne kan sees hele tiden
        - Kameraet følger ikke døde spillere
        - Ingen exception blir kastet dersom alle spillere er døde
    - Arbeidsoppgaver:
        - Opprett en klasse for ett custom kamera
        - Lag metode(r) for å regne ut kameraposisjon
        - Lag sjekk og håndter kameraposisjon når spillerne er døde

- Som spillere ønsker vi at kameraet zoomer ut slik at alle spillerene kan være innenfor kameraet over lengre distanser
    - Akseptansekriterier:
        - Kameraet zoomer ut basert på de ytterse spillerene
        - Kameraet zoomer ut når spillerene går over en viss distanse bort fra hverandre
        - Kameraet zoomer inn når spillerene går under en viss distanse bort fra hverandre
        - Kameraet har en maks og min zoom
        - Håndtere døde spillere
    - Arbeidsoppgaver:
        - Lag en metode som håndterer zoom nivåene på kameraet
        - Lage en sjekk som ser om spillerene er innenfor eller ovenfor en viss distanse og håndtere zoom nivåene basert
          på det

- Som spillere ønsker vi at kameraet ikke går utenfor level grenesene slik at vi ikke ser utenfor det utviklerene ønsker
  oss å se
    - Akseptansekriterier:
        - Kameraet beveger seg ikke uten for level grensene
    - Arbeidsoppgaver:
        - Lage en metode som sjekker om den gjennomsnittlige positionen til spillerene vil sette kameraet slik at man
          kan se utenfor grensene
        - Om kameraet vil gå utenfor, beregn en kameraposition som ikke går utenfor

- Som spillere ønsker vi at vi ikke kan gå utenfor kameraet slik at vi kan se hvor vi er.
    - Akseptansekriterier:
        - Det finnes vegger utenfor de vertikale delene av kameraet
        - Spillerene kolliderer med- og kan ikke gå utenfor veggene
    - Arbeidsoppgaver:
        - Lage et GameWall objekt
        - Spawne to vegger når levelene lages
        - Lage en metode i GameCamera som flytter veggene basert på kamera positionen

### Start-skjerm ved oppstart / game over:

- Som utvikler ønsker jeg muligheten for å sette en start- og game over skjerm slik at jeg har mer kontroll over
  spillerens opplevelse og kan til en viss grad kontrollere tempoet i spillet.
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
        - La GameModel holde styr på hvilken skjerm som er aktiv (for å bestemme hva som skal skje ved bytting av
          skjerm)
            - Ved bytting fra GameoverScreen til GameScreen skal samme levelet lastes på nytt
            - Ved bytting fra Start/MenuScreen til GameScreen skal første levelet lastes

### Muligheten for å velge antall spillere ved oppstart:

- Som spiller ønsker jeg muligheten for velge antall spillere ved oppstart, slik at jeg som spiller får en bedre
  spillopplevelse når jeg selv kan velge hvor mange spillere som skal være med i spillet.
- Som utvikler ønsker jeg muligheten for å velge antall spillere ved oppstart, slik at jeg som utvikler slipper å
  harkode inn et fast antall spillere, og får det lettere å testkjøre spillet.
    - Akseptansekriterier:
        - Ved oppstart, skal det vises tekst som forklarer hvordan man kan velge antall spillere som man ønsker i
          spillet.
        - Sørge for at man ikke kan endre antall spillere når spillet først er i gang.
        - Sørge for å håndtere ugyldige verdier for antall spillere.
    - Arbeidsoppgaver:
        - La GameController sjekke etter input som angir hvor mange spillere en spiller ønsker ved startskjermen.
        - La GameController sette antall spillere i GameModel klassen.
            - Sette antall spillere i GameModel til en standardverdi (1 spiller).
            - Lage en metode som endrer denne verdien.

### Muligheten for at spillere kan hoppe opp gjennom og droppe ned gjennom plattformer:

- Som spiller ønsker jeg muligheten for å kunne hoppe opp gjennom og droppe ned gjennom plattformer for å gi spilleren
  flere muligheter for å bevege seg i spillet.
    - Akseptansekriterier:
        - Spillere skal kunne hopppe opp gjennom plattformer uten å kollidere med plattformens sin body. Spilleren må
          kunne hoppe upåvirket gjennom platformen.
        - Når spilleren står oppå plattformen, skal den ved hjelp av å trykke på en knapp kunne droppe ned gjennom
          plattformen. Det er kun når spilleren trykker på knappen at spilleren skal ha muligheten til dette.
        - Funksjonaliteten skal virke individuelt på de ulike spillerne, så dersom flere spillere står oppå plattformen,
          så skal bare spilleren som trykker på knappen droppe ned gjennom plattformen.
    - Arbeidsoppgaver:
        - Sjekke kollisjon mellom spillerens fot og plattformer, og deretter sette en variabel i Player klassen for å
          indikere at spilleren befinner seg på en plattform.
        - Lage en ny Category Bit for spiller som ikke kolliderer med Mask Bits-ene til Platform.
        - Lage en metode for å ta seg av logikken når man trykker på knapp ned. (Bruk setCategoryFilter() metoden fra
          BodyHelper klassen til å implementere logikken)

### Bedre Goomba bevegelse:

- Som spiller ønsker jeg at fiender i spillet skal kunne bevege seg bedre og smartere for at spillet skal være
  morsommere og mer utfordrende å spille.
    - Akseptansekriterier:
        - Goomba objekter skal kunne se etter spillere og bevege seg mot dem (angripe) om de er innenfor en viss
          rekkevidde.
        - Ellers skal Goomba objekter bevege seg frem og tilbake.
    - Arbeidsoppgaver:
        - Lage en rekkevidde sensor til Goomba objekter.
        - Sjekke kollisjon mellom rekkevidde sensoren og spillere.
        - Finne posisjonen til spilleren som Goomba objektet skal angripe og implementer logikk for å bevege seg i den
          retningen.
        - Implementer bevegelses logikk når det ikke er spillere innenfor rekkevidden.

### Muligheten for at spillere kan drepe Goomba objekter:

- Som spiller ønsker jeg muligheten for å kunne drepe fiendeobjekter (Goomba) slik at spillere har flere
  funksjonaliteter og gjør spillet morsommere.
    - Akseptansekriterier:
        - En spiller skal kunne drepe Goomba objekter med å hoppe over de og trykke ned.
        - Spilleren skal ikke miste HP under et slikt angrep.
        - Goomba objektet dør etter et slikt angrep og kan ikke lenger interagere med andre spillere.
    - Arbeidsoppgaver:
        - Sjekke/skille for kollisjonstilfellene i når en spiller kan drepe en Goomba og når den selv skal ta skade av
          Goomba.
        - Ta hånd om Goomba når den skal dø. Lag en setDead() metode i Goomba klassen. Endre categoryBits og maskBits
          f.eks.

### Flytte informasjon om level fra GameModel til egen klasse:

- Som prosjektdesigner ønsker jeg at informasjon og logikk om levels ligger i en egen klasse for å bedre følge
  single-responsibility principle og gjøre GameModel mer leselig.
    - Akseptansekriterier:
        - Pakken model inneholder en klasse Level
        - Klassen Level inneholder feltvariabler og metoder for å holde styr på spillverdenen og -objekter
        - Når et Level instantiseres opprettes en verden, alle objekter levelet skal inneholde og HUD ut fra gitt
          TiledMap
        - Klasser som kun var avhengige av levelinformasjonen i GameModel bruker Level i stedet
    - Arbeidsoppgaver:
        - Opprett klassen Level
        - Flytt relevant kode fra GameModel til Level
        - Bruk Level i GameModel
        - Bruk Level alle steder spesifisert av akseptansekriterie

### Legge til ny fiende:

- Som spiller ønsker jeg å ha flere fiender i spillet for å ha en varians av måter å håndtere situasjoner og skape
  interesante spillopplevelser.
    - Akseptansekriterier:
        - Unik fiende som har forskjellig oppførsel fra Goomba.
        - idé:
            - fiende som beveger seg mot spiller om den er innenfor en viss radius fra objektet.
            - flyter i luften.
    - Arbeidsoppgaver:
        - Lage ett nytt objekt til verden.
        - Legge til egen oppførsel til objektet.
        - Få objektet til å gjøre skade mot spilleren.
        - Få objektet til å følge spilleren

### Legge delay på game-over skjerm

- Som spiler ønsker jeg å se spillerkarakteren før spillet blir game over slik at jeg kan se hva som drepte meg.
    - Etter siste spiller dør skal det ta litt tid (1-2 sekunder) før spillet får game-over.
    - I denne perioden skal kamera stå i ro hvor spilleren dør, og en kort døds animasjon skal spille.
- Arbeidsoppgaver:
    - Legg til en "Timer.Schedule" i set-dead som aktiverer en bool.
    - Bruk denne bool-en til å finne når det er game over.

### Mulighet for å pause spillet:

- Som spiller ønsker jeg muligheten for å kunne sette spillet på pause for å få en bedre opplevelse i spillet.
    - Akseptansekriterier:
        - Når spilleren trykker på tast P, skal spillet settes på pause.
        - Det skal være tydelig at spillet er satt på pause. Viktig at det visuelle er tydelig på at spillet er satt på
          pause.
            - God og tydelig tekst. Teksten skal også forklare spilleren hvordan man starter spillet igjen.
            - Legge til et filter over skjermen som gjør at spillets farger dempes.
        - Når spilleren trykker på P mens spillet er i pause, skal spillet startes opp igjen.
        - Når spillet starter oppatt, skal spillet være i samme tilstand som da spilleren trykket på pause.
        - Brukeren skal ikke ha muligheten til å sette spillet i pause når spilleren er i startmeny, gameover meny eller
          i nextLevel meny.
    - Arbeidsoppgaver:
        - Legge til tabeller i HUD klassen som holdes skjult så lenge spillet ikke er satt til pause.
        - Legge til filter i HUD klassen som holdes skjult så lenge spillet ikke er satt til pause.
        - Legge til funksjonalitet i GameController klassen som sjekker om tast P er trykket inn (og i en lovlig
          tilstand) og implementer logikk i GameModel for å håndtere dette.
        - Visuelt se at tekst og filter er tydelige nok til at en spiller vet at spillet er satt til pause og hvordan
          man kan fortsette spillet.

### Legge til musikk og lyd-effekter:

- Som spiller ønsker jeg at spillet jeg spiller inneholder musikk og lyd-effekter slik at spillet føles mer livlig ut.
    - Akseptansekriterier:
        - Spillet skal kunne ha forskjellig musikk fra "level" til "level".
        - Musikken skal settes på pause når spillet settes på pause.
        - Musikken skal starte på nytt om "gameover" eller "restart".
        - Lydeffekter til forskjellige handlinger i spillet som: hoppe, plukke opp mynter, drepe fiender, gameover osv.
        - Musikken og lyd-effektene skal lastes inn på en effektiv måte. Unngå flere innlastinger av samme filene.
    - Arbeidsoppgaver:
        - Lage en klasse som tar hånd om innlasting av filer og gjør det lett for andre klasser å hente ut musikk og
          lyd-effekter.
        - Finne musikk og lyd-effekter til prosjektet.
        - Legge til lisenser til filene man har brukt.

### Animasjoner på objekt bevegelse

- Som spiller ønsker jeg animasjoner på spilleren og andre objekter for at spillet skal føles mer levende ut og slik at
  ulike bevegelser er mer tydelige. Dette vil gjøre spillet mer visuelt tiltrekkende
    - Akseptansekriterier:
        - Player, Goomba, Floater og Goal har diverse animasjoner
            - Player: idle, walk, jump, slide, drop, dead
            - Goomba: walk, dead
            - Floater: move
            - Goal: idle
        - En animasjon består av forskjellige teksturer som vises med et gitt tidsinterval
        - Objektene har en getFrame() metode som styrer logikken for hvilken animasjon som skal vises
    - Arbeidsoppgaver:
        - Last inn en texture for hvert objekt
        - Del texturen inn i regioner for hver frame av animasjonen
        - Bruk Animation til å holde styr på ulike animasjoner
        - Implementer getFrame() som oppdaterer hvor lang tid det er gått og velger riktig frame av gjeldende animasjon