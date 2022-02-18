# **Obligatorisk oppgave 1:**
Prosjekt Plattformspill - oppstart**

[https://git.app.uib.no/inf112/22v/lectures/-/blob/master/prosjekt/obligatoriskOppgave1.md](https://git.app.uib.no/inf112/22v/lectures/-/blob/master/prosjekt/obligatoriskOppgave1.md)

**Deloppgave 1: Organiser teamet**

**Team navn:**

Grabbane

**Teamet består av:**

- Espen Lade Kalvatn
- Martin Andvik Øvsttun
- Tobias Soltvedt

- Kristoffer Jensvoll-Johnsen

**Kompetanse:**

Alle på teamet går 4. semester på bachelor i Data Science, og har tatt de relevante fagene:

- INF100
- INF140
- INF101
- INF102
- INF161
- MAT121
- MAT111

Utenom studiet har teammedlemmene lite til ingen erfaring med større prosjekter. Størsteparten av kompetansen kommer fra de overnevnte studiene.

Her er opprettet Git gruppe med tilhørende prosjekt:

[https://git.app.uib.no/grabbane](https://git.app.uib.no/grabbane)

**Roller:**

Vi har bestemt oss for å vente litt med å fordele roller fordi vi er usikker på hvilke roller som vil hjelpe oss under utviklingen. Så langt ser vi at en team/prosjektleder kan være fornuftig å ha fordi det blir lettere for de andre teammedlemmene å jobbe når én har hovedansvaret for organisering og oversikt over hva som må gjøres.

Vi bruker GitLab sin innebygde issue tracker med tilhørende board for å holde styr på arbeidsoppgaver. Vi tenker at å minimere antall plattformer og applikasjoner vi bruker vil være mer oversiktlig for alle. I tillegg kan det være lettere å ta i bruk hvis man slipper å åpne et annet program eller gå til en annen nettside for å legge til en issue.

**Deloppgave 2: Velg og tilpass en prosess for laget**

**Prosjektmetodikk:**

Vi baserer oss hovedsakelig på Kanban, men henter litt fra Scrum og XP. Vi ønsker &quot;hybrid programmering&quot;, dvs av og til parprogrammering og av og til individuell. Vi ønsker å følge klassisk testdrevet utvikling, men tenker det kan være vanskelig å gjøre hele tiden med tanke på vår erfaring med spillutvikling (testing av grafikk f.eks.).

Vi vil ha ukentlige møter på gruppetimene, og et lite oppsummeringsmøte på slutten av fredagene. Dette er mer i linje med Scrum, men vi ønsker ikke sprints. Møtene skal brukes til å få oversikt over hva som er nødvendig å gjøre i nærmeste framtid og fordele arbeid for den kommende uken. Dette er for å holde oss organisert og oppdatert på hva hele teamet holder på med. Samtidig er det viktig å påpeke at arbeidet som blir fordelt ikke er fastsatt, men heller dynamisk, et forslag til hvordan vi skal jobbe i løpet av uken.

Grunnet hvordan studiehverdagen ser ut, er det viktig at vi kan være fleksible. Derfor tenker vi at Kanban er bedre for oss, siden det er færre retningslinjer å følge. Det er vanskelig for teammedlemmene å finne tidspunkt å jobbe sammen på, og derfor går vi ikke helt ut med parprogrammering.

**Møter og hyppighet av dem:**

- Fysisk møte hver mandag 12:15-14:00 (gruppetimen gruppe 1)
  - Organisere uken, definere og fordele oppgaver osv.
  - Kan brukes til parprogrammering dersom det første punktet er ferdig
  - Kan brukes til å diskutere vanskeligere oppgaver
- Digitalt møte hver fredag 13:00-13:30
  - Oppsummering av uken
    - Oppfølging for å sjekke at alle er i rute

**Kommunikasjon mellom møter:**

- Discord kanal
- Intern kommunikasjon mellom teammedlemmer
- Issue tracker
- Parprogrammering

**Arbeidsfordeling:**

- Fordele type arbeidsoppgaver jevnt. Ta hensyn til vanskelighetsnivå

**Oppfølging av arbeid:**

- Oppsummeringsmøte fredag, kommunikasjon gjennom discord og prosjektbord
- Når en arbeidsoppgave er ferdigstilt, skriver man en melding i felles kommunikasjonskanal (Discord) f.ex &quot;Donzo!&quot;. Dette er både for oppfølging av hverandre, men også for å motivere hverandre for å bli ferdige med delprosjekter.
- Dersom noe er vanskelig må teammedlemmet ta kontakt med de andre for å få hjelp

**Deling og oppbevaring av felles dokumenter, diagram og kodebase:**

- GitLab (hovedkilde)
- Discord
  - lett til å kjapt dele bilder eller mindre filer siden vi bruker programmet til generell kommunikasjon uansett
- Google docs
  - Lettere å skrive i samtidig og mer oversiktlig enn en markdown fil

**Kodekonvensjon:**

- camelCase
- JavaDoc
- English coding language (Classes, Interfaces, variables and comments etc.)

**Git arbeidsflyt:**

- Feature branches
  - Vi tenker det er mindre fare for konflikter og mer oversiktlig med feature branches. Master grenen skal holdes ren og stabil.
- Commit ofte
  - Dette gjør det lettere for andre teammedlemmer å se hva som har blitt gjort, og bidrar til bedre dokumentasjon. Naturligvis skal commit-meldingene også være deskriptive, noe som blir lettere dersom commiten ikke er så stor.

**Deloppgave 3: Få oversikt over forventet produkt**

**Beskrivelse av det overordnede målet for applikasjonen:**

Et 2d platforming spill hvor det er mulig å bevege seg rundt ved å hoppe, løpe og falle hvor en spiller brikke beveger seg mot et mål på slutten av en bane mens spiller brikken samler objekter og unngår/slåss mot fiender på veien dit med muligheten å spille med venner

**Liste over brukerhistorier til systemet basert på MVP-kravene:**

Prioritert - topp til bunn

- Vise et spillebrett
  - Som spiller trenger jeg en grafisk framstilling av spillverdenen for å kunne navigere den lett.
  - Som spiller ønsker vi å ha et fungerende level for at spillet skal fungere.
    - Trenger at spillverdenen er bredere (og evnt høyere) enn selve spillvinduet. Kameraet i spillet må dermed ha muligheten til å flytte på seg i forhold til spilleren.
    - Spillbrettet kan ha flere dybde nivåer (Foreground, Background 1, Background\_2 , …).
- Vise spiller på spillebrett
  - Som spiller ønsker jeg en spiller visualisert på spillebrettet slik at jeg kan se hvor jeg er på spillebrettet og hvor jeg er i forhold til objektene rundt i spillverdenen.
  - Som programmerer trenger jeg et spiller-objekt for å kunne holde styr på og endre på tilstanden (posisjon, fart, osv.) til spilleren.
  - Som programmerer trenger jeg en grafisk framstilling av spilleren for å se hvordan spilleren interagerer med spillverdenen.
- Flytte spiller (vha. taster e.l.)
  - Som spiller trenger jeg en måte å kunne bevege meg rundt på spillbrettet, gjerne ved standard kontroller som piltaster eller WASD
- Spiller interagerer med terreng
  - Som spiller trenger jeg å kunne interagere med terreng for å bevege meg i forhold til spillverdenen
- Spiller har _poeng_ og interagerer med poeng gjenstander
  - Som spiller ønsker jeg å ha et mål som jeg kan sikte på når jeg spiller, poeng kan være en god løsning. Om jeg har poeng trenger jeg poeng gjenstander som enten øker eller senker poengsummen min. _Neuron activated_
- Vise fiender/monstre; de skal interagere med terreng og spiller
  - I et platformer spill trenger jeg som spiller en utfordring når jeg navigerer meg rundt i verdenen. Jeg ønsker meg derfor fiender/monstre som interagerer med meg og terrenget rundt i spillverden.
- Spiller kan dø (ved kontakt med fiender, eller ved å falle utenfor skjermen)
  - Som spiller ønsker jeg noe risiko i spillet. For eksempel ved at jeg har en viss mengde liv, og om jeg tar nok skade fra fiender eller om jeg faller ut av spillverdenen vil jeg dø.
- Mål for spillbrett (enten et sted, en mengde poeng, drepe alle fiender e.l.)
  - Som spiller ønsker jeg et mål jeg kan jobbe mot hvert nivå. Det kan være et mål mot slutten av nivået, at jeg samler en mengde poeng eller dreper alle fiendene som er i nivået.
- Nytt spillbrett når forrige er ferdig
  - Som spiller vil jeg kjede meg om spillet kun har ett nivå, derfor ønsker jeg meg flere nivåer som jeg kan jobbe mot. Når jeg blir ferdig med et nivå, ønsker jeg å gå videre til et nytt ett.
- Start-skjerm ved oppstart / game over
  - Som spiller ønsker jeg en meny til å navigere mellom nivåene. Jeg ønsker også en startskjerm som gir meg en introduksjon til nivået jeg skal spille og en game over skjerm når om jeg dør.
- Støtte flere spillere (enten på samme maskin eller over nettverk)
  - Som spiller ønsker jeg muligheten til å spille med vennene mine, enten med flere spillere på samme maskin, over LAN eller over nettet.

- For hver brukerhistorie, skal dere ha akseptansekriterier og arbeidsoppgaver, samt beskrivelse av hvilke krav brukerhistoriene oppfyller (dette lager dere kun for historier dere er ferdige med, holder på med, eller skal til å begynne med)

- En prioritert liste over hvilke brukerhistorier dere vil ha med i første iterasjon (altså frem til levering av denne oppgaven, se deloppgave 4 for forslag).

- (Frivillig) Krav til MVP er gitt i neste deloppgave. Dersom dere ønsker å utvide denne listen med ytterligere funksjonalitet, skal det også med som en del av denne spesifikasjonen.

## **Deloppgave 4 (uke 7): Kode**

En del av leveransen for denne oppgaven skal være kode – men

Fokuset i starten bør være på å levere et minimum viable product (MVP) så raskt som mulig (vi regner med at flesteparten klarer å nå gjennom MVP til andre oblig). Krav til MVP:

1. Vise et spillebrett
2. Vise spiller på spillebrett
3. Flytte spiller (vha. taster e.l.)
4. Spiller interagerer med terreng
5. Spiller har _poeng_ og interagerer med poenggjenstander
6. Vise fiender/monstre; de skal interagere med terreng og spiller
7. Spiller kan dø (ved kontakt med fiender, eller ved å falle utfor skjermen)
8. Mål for spillbrett (enten et sted, en mengde poeng, drepe alle fiender e.l.)
9. Nytt spillbrett når forrige er ferdig
10. Start-skjerm ved oppstart / game over
11. Støtte flere spillere (enten på samme maskin eller over nettverk)

**Deloppgave 5: Oppsummering**

Utfør et kort prosjekt-retrospektiv og diskuter hva som gikk bra, hva som ikke fungerte helt som forventet, hva (om noe) som ikke virket i det hele tatt, og eventuelle nye aktiviteter eller verktøy som teamet vil prøve ut i løpet av neste obligatoriske oppgave. Diskuter hvorfor ting fungerte eller ikke fungerte. Skriv opp en kort oppsummering av diskusjonen, og last opp til team repo-et.

På slutten av denne oppgaven kan dere gjøre en liten vurdering av hvor bra dere traff på oppgaven. Dette kan dere bruke til å justere hvor mange oppgaver dere tenker å få inn i neste iterasjon, som da leveres med obligatorisk oppgave 2.


Hva gikk bra:
  - Discord kommunikasjon
    - Lav terskel for å kommunisere
  - Samarbeid gjennom IntelliJ's Code With Me
  - Par/teamprogrammering
    - Spesielt nyttig på vanskeligere oppgaver
    

Ikke som forventet:
  - Code with me i intelliJ Idea
    - gikk veldig bra og gjorde det veldig lett å jobbe i lag, men alle commitsene blir credited til den som hoster
  - Ikke mulig å assigne flere brukere til samme "Issue" på GitLab sitt "Issue Board" uten premium versjon.
    - Dette gjør at vi har implimentert et konvensjon om at vi legger til klammeparanteser med initialene til hvert medlem som har jobbet på samme "issue".
      - M: Martin
      - E: Espen
      - T: Tobias
      - K: Kristoffer
  - proiritering av arbeid
    - det var vanskelig å proritere arbeid i starten siden alt var veldig linært, etter hvert som prosjektet går vil det bli meir åpent og vi må jobbe meir med å prioritere "issues"

Ikke bra:
  - Testing (komplett mangel)
    - vanskelig å teste når man ikkje veit heilt hva som skjer i programmet
  - si hvem som jobber med hva

