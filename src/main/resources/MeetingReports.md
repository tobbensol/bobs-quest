# Prosjekt Plattformspill

## Referater

Grunnet mangel på informasjon før andre obligatorisk innlevering, har vi ikke tidligere referater fra møter før fredag
18. mars 2022.

### Fredag 18. mars 2022

#### kl 13:00-13:30

Digitalt oppsummeringsmøte

Oppmøte: Alle var tilstede

Vi startet møte med å gå gjennom siste ukens endringer i koden. Her presenterte vi koden og hva vi har gjort. Ukens
endringer gikk ut på:

- Ny klasse &quot;GameObjectFactory&quot; som skal ta for seg å enkelt lage forskjellige &quot;GameObject&quot; objekter
  i &quot;GameModel&quot;.

- Endringer i hvordan Coin-klassen fungerer. Her har vi endret måten vi lager Coins på, slik at det ligner mer på
  hvordan vi lager andre objekter.
- Endringer i hvordan TiledMapHelper &quot;parser&quot; ulike objekter fra TiledMap. Målet er å generalisere hvordan vi
  &quot;parser&quot; objekter.
- Endringer/generalisering av BodyHelper klassen slik at den skal kunne lage &quot;body&quot;er til alle objekter i
  spillet (både &quot;environment&quot; objekter og &quot;gameObject&quot; objekter).

Videre i møte snakket vi litt om hva vi må gjøre fremover, men dette kommer vi mer tilbake til på mandagsmøte. Litt av
det som vart diskutert var:

- Hvordan vi kan legge til et mål med spillet.
- Arbeide med den andre obligatoriske innleveringen.

Etter møte jobbet vi sammen med den andre obligatoriske innleveringen.

### Mandag 21. mars 2022

#### kl 12:15-14:00

Fysisk møte

Oppmøte: Alle tilstede

I dagens møte gikk vi gjennom følgende:

- Vi diskuterte rapporten som skal levers inn 26. mars. Hva som gjenstår og hvem som skal gjøre de ulike delene.
- Vi diskuterte hvordan vi kan lage en enkel kamera implementasjon som fungerer grett nok (bedre enn det opprinnlige).
  Denne oppgaven ønsket Kristoffer å jobbe med.
- Vi diskuterte også kravene til andre oblig. Her trenger vi å skrive en README fil (Espen), skrive utdype en del av
  brukerhistoriene våre (Martin), lage klassediagram (Martin) og generelt svare på spørsmål i rapporten (alle).
- Vi diskuterte også hvordan vi skal løse MVP krav 10 (Start/game over skjerm). Dette har Tobias sett litt på over
  helgen, og fortsetter med dette denne uken. Vi andre vil hjelpe til om nødvendig.

### Fredag 25. mars 2022

#### kl 13:00-13:30

Digitalt oppsummeringsmøte

Oppmøte: Alle var tilstede

Vi startet møte med å gå gjennom siste ukens endringer i koden. Her presenterte vi koden og hva vi har gjort. Ukens
endringer gikk ut på:

- Vi har lagt til ulike skjermen (start, game over, og neste nivå skjerm).
- Vi har lagt til mulighet for å velge antall spillere.
- Vi har laget en egen kamera klasse som tilfører ny funksjonalitet som zoom og gjennomsnitts posisjon.

Vi har også gjort andre endringer som:

- Jobbet med rapport (brukerhistorier osv)
- Laget README.
- Laget BugReplication/manuelle tester.

Videre i møtet gikk vi gjennom siste delen av rapporten som skal leveres inn senere i dag.

### Mandag 28. mars 2022

#### kl 12:15-14:00

Fysisk møte

Oppmøte: Fysisk: Martin, Espen, Kristoffer, Digitalt: Tobias 

I dagens møte diskuterte vi hva vi skulle gjøre denne uken:

- Tobias skal prøve å finne ut forrige ukes problem med å laste inn nye nivåer i spillet. Dette er trolig en eller annen feil i måten nivået blir laget på i Tiled.
- Kristoffer fortsetter med kamera implementasjonen som han jobbet med forrige uke. Her er målet å implementere funksjonen slik at kameraet ikke kan gå utenfor mappet og at spillerne ikke kan gå utenfor kameraet.
- Espen har sett litt på “player movement” og fortsetter med det. Her er det å prøve å få bedre spiller bevegelser og gjøre det slik at det likt på alle maskiner (deltaTime). Eventuelt begynne på implementasjon av muligheten for en spiller og hoppe opp/dette ned gjennom plattformer.
- Martin ser på hvordan vi skal separere informasjonen om hvert enkelt level og implementere en egen level klasse. Dette for å dele opp GameModel klassen.

Avhengig av hvor mye jobb dette er og hvor lang tid dette tar, kan det hende vi diskuterer flere oppgaver. 


### Fredag 1. april 2022
#### kl 13:00-13:30

Digitalt oppsummeringsmøte

Oppmøte: Alle var tilstede

Vi startet møte med å gå gjennom siste ukens endringer i koden. Her presenterte vi koden og hva vi har gjort. Ukens endringer gikk ut på:
- Tobias fikk fikset problemet med å laste inn nye nivåer i spillet. Tobias har laget en mal for å lage nye nivåer (setupMap) der oppsettet med alle typer objekter er lagt inn på riktig format.
- Kristoffer fikk implementert kamerafunksjoner slik at kameraet ikke kan gå utenfor mappet og at spillere ikke kan gå utenfor kamera. Zoom funksjonen er også blitt forbedret.
- Martin har implmentert en Level klasse som er med på å dele opp hva GameModel klassen skal holde på (Single resposibility prinsippet).
- Espen har implementert funksjonalitet der spillere kan hoppe opp gjennom plattformer og droppe ned gjennom de med å trykke ned. Også implementert funksjonalitet på hvordan Goomba objekter beveger seg. De følger nå etter spillere om de er innenfor en viss rekkevidde. Spillere kan også drepe Goomba objekter. “Player movement” og “deltaTime” var vanskeligere enn antatt. Her har vi ikke funnet en løsning enda.

Videre i møte diskuterte vi nye bugs som vi har funnet og mulig årsak til disse. Disse har blitt lagt til i BugReport. Vi bestemte oss også for hvilken kameraløsning som vi ønskte å gå fremover med.


### Mandag 4.april 2022
#### kl 12:15-14:00
Fysisk møte
Oppmøte: 

Fysisk: Tobias, Espen, Digitalt: Kristoffer

I møte diskuterte vi “player movement” og problemene vi har med at spillet kjører med ulik FPS og dermed påvirker fysikken. Vi endte til slutt på å låse FPS-en på 60 og heller skrive i systemkravet i README filen til at pcen som kjører spillet må kunne kjøre spillet i 60 FPS. Videre i møtet snakket vi om hva vi skulle gjøre denne uken. Siden det er innlevering fredag, vil alle jobbe med rapporten i tillegg.
- Martin har sett på JUnit testing av spillet og har begynt med dette. Han fortsetter med dette denne uken.
- Espen har endret på hvordan vi gjør “player movement” i helgen og Tobias har sett et problem vi har med en boolean variabel “grounded” som ikke gir rett verdi i overgangen mellom “ground” og “slope”. Kristoffer vil justere på parametere for at spillet skal føles best mulig.
- Kristoffer skal også skrive manuelle tester og brukerhistorie for kamera implementasjonen som han laget forrige uke.
- Tobias skal legge til et nytt fiende objekt i spillet. Han skal også se på en bug relatert til “grounded” fixen som har implementerte i helgen.
- Espen har ingen spesifikke oppgaver, men skal hjelpe til der det trengs. Refaktorere/generalisere klasser osv kan være mulig.


### Fredag 08. april 2022
#### kl 13:00-13:30

Digitalt oppsummeringsmøte

Oppmøte: Alle var tilstede

Vi startet møte med å gå gjennom siste ukens endringer i koden. Her presenterte vi koden og hva vi har gjort. Ukens endringer gikk ut på:
- Martin har jobbet en del med JUnit testing. Laget JUnit tester for Player og GameModel.
- Kristoffer har skrevet manuelle tester, brukerhistorier og dokumentasjon for kamera klassen.
- Tobias har jobbet med å legge til en ny fiende objekt. Han har også sett på buggen relatert til "grounded" og hopping. Har også lagt til en "delay" i gameover.
- Espen har implementert pause funksjonalitet og generalisert kontakter mellom player og fiende objekter. Laget et "Enemy" interface som fiende obekter implementer. Dette interfacet utvider et nytt interface IGameObject som GameObject implementere.

Videre jobbet vi sammen med obligatorisk innlevering 3 som skal leveres inn senere i dag.


### Mandag 11. april 2022
#### kl 12:00-12:30

Digitalt møte

Oppmøte: Alle tilstede

I møtet snakket vi om hva vi ønskte å få gjort iløpet av påskeferien og hvem som skal gjøre hva. Vi kom frem til at:
- Kristoffer: Skal re-skalere tileset-ene til de andre pakkene med teksturer slik at vi kan lage nye levler i spillet med andre teksturer. Han skal også se på å kanskje lage et nytt level.
- Martin: Skal fortsette med JUnit testing av spillet.
- Espen: Skal forsøke å legge til musikk og lydeffekter til spillet. Skal også se på muligheten til å lage et bedre menysystem med funksjonelle knapper.
- Tobias: Skal se på muligheten til å legge til et nytt objekt i spillet og eventuelt lage et nytt level. 


### Fredag 15. april 2022
Dette møtet gikk vekk på grunn av påskeferie


### Onsdag 20. april 2022
(Opprinnelig Mandag 18. april, flyttet på grunn av påskeferie)
#### kl 12:00-13:30
Digitalt møte

Oppmøte: Alle tilstede

I møte gikk vi gjennom hva hver av oss fikk gjort siden sist mandag.
- Espen: Implementert et nytt Menysystem og har fått lagt til musikk og lydeffekter. Her er det litt mer jobb som må gjøres før dette kan merges inn i master branchen.
- Kristoffer: Skalerte opp og ryddet/laget nytt system i  tileset-ene. Fikset player movement.
- Tobias: Fikset opp i bit-systemet i programmet. Laget et nytt objekt (moving platform) og fikset litt her og der i koden.
- Martin: Har implementert flere JUnit tester.

Videre i møte snakket vi om hva vi skal gjøre fremover. Siden fristen på prosjektet nærmer seg, legger vi fokuset på å fullføre det vi holder på med fremfor å legge til mer funksjonalitet. Vi fordelte arbeidsoppgaver for uken:
- Espen: Bli ferdig med menysystemet/musikk og lydeffekter og refaktorere deler av koden for å få det på et ønskelig format.
- Kristoffer:  Fokusere på å lage nye levler til spillet.
- Tobias: Fokusere på å lage nye levler til spillet.
- Martin: Se på muligheten til å legge til animasjoner i spillet (spiller bevegelse osv), eventuelt se på å forberede generell kode i prosjektet.


### Fredag 22. april 2022
####kl 13:00-13:30
Digitalt møte

Oppmøte: Alle tilstede

Vi startet møte med å gå gjennom siste ukens endringer i koden. Her presenterte vi koden og hva vi har gjort. Ukens endringer gikk ut på:
- Martin: La til animasjoner for spiller og Goomba bevegelse.
- Espen: Flyttet logikken til knappene i menysystemet til GameController klassen som igjen kaller på metodene i GameModel. Musikk, lydeffekter, volum holder til i AudioHelper klassen.
- Tobias: Fortsatt med level 2, funnet mulige teksturer, fikset buggen “neste level flashet (1 frame) før begynne på neste level”, floater movement.
- Kristoffer: Begynt på level 3, oppdatert player movement, tynnere plattformer, laget edge objekter (hindre at goomba detter av mappet)


### Mandag 25. april 2022
####kl 12:15-14:00
Fysisk møte

Oppmøte: Alle tilstede

Dette er det siste mandagsmøte før prosjektet skal leveres inn fredag 29. april. Møtet gikk da ut på å gå gjennom hva som må gjøres den siste uken. 
Hovedfokuset blir å gjør ferdig det vi holder på med og ikke begynne med noe helt nytt før innleveringen. 
Arbeidsoppgavene denne uken blir da å gjøre ferdig så mange levler (3-4 totalt) som vi klarer, få byttet ut teksturene til de forskjellige objektene i spillet, 
svare på spørsmålene knyttet til innleveringen og finpusse koden der det trengs.
