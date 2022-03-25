#Prosjekt Plattformspill

##Referater

Grunnet mangel på informasjon før andre obligatorisk innlevering, har vi ikke tidligere referater fra møter før fredag
18. mars 2022.

###Fredag 18. mars 2022

####kl 13:00-13:30

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

###Mandag 21. mars 2022

####kl 12:15-14:00

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

###Fredag 25. mars 2022

####kl 13:00-13:30

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