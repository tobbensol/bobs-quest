Prosjektstruktur:
- Som en utvikler ønsker jeg en bedre prosjektstruktur slik at programmet blir lettere å utvide.
    - Akseptansekriterier:
        - Prosjektet har samme funksjonalitet som før
        - Prosjektstrukturen følger MVC-prinsippet
    - Arbeidsoppgaver:
        - Opprette en model for å holde på spillogikken
        - Flytte funksjonalitet som ikke har med view å gjøre inn i modellen

Spillerteksturer:
- Som spiller ønsker jeg å ha forskjellige teksturer for forskjellige tilstander spilleren befinner seg i.
    - Akseptansekriterier:
        - Forskjellige teksturer for de forksjellige tilstandene en spiller kan befinne seg i (Stå, gå, hoppe og falle).
        - Teksturene til spillerene må peke i samme retning som spilleren beveger seg i.
    - Arbeidsoppgaver:
        - Lage/sette sammen en png-fil (64x64 px) med flere teksturer etter hverandre (alle vendt i samme retning)
        - Legge til funksjonalitet for å finne hvilken tilstand spilleren er i, samt retning spilleren beveger seg i.
        - Legge til funksjonalitet for å velge rett tekstur til rett tilstand.

Større spillbrett:
- Som utvikler ønsker jeg et større spillbrett for å teste mer funksjonsalitet.
    - Akseptansekriterier:
        - Det nye brettet inneholder poenggjenstander, mulighet for fiender og død
        - Brettet lastes inn og vises på skjermen
    - Arbeidsoppgaver:
        - Lag nytt brett med overnevnt funksjonalitet
        - Last inn brett i TileMapHelper. Edit: Finn problem som hindrer lasting av brett
        - Exception handling for lasting av nytt brett

Poeng og poenggjenstander (Spiller har poeng og interagerer med poenggjenstander):
- Som spiller ønsker jeg et poengsystem slik at jeg føler meg belønnet for å spille spillet bra
    - Akseptansekriterier:
        - Spillmodellen holder styr på poeng
        - En poeng-teller viser antall poeng på skjermen
        - Modellen har en poenggjenstand
        - Poenggjenstanden vises på skjermen
        - Poenggjenstanden forsvinner ved interaksjon og poengtelleren oppdateres
    - Arbeidsoppgaver:
        - Legg til variabel i modellen som holder på antall poeng
            - (per spiller?)
        - Vis antall poeng på skjermen
        - Lag/finn en poenggjenstand og legg til i brettet (sammenheng med "større spillbrett" historie)
        - Legg til funksjonalitet for å sjekke kontakt mellom spiller og poenggjenstand
            - Objektet må ha en body med en gitt contact type
        - Ved kontakt, fjern objektet fra brettet og legg til poeng i modellen
            - Pass på at poengtelleren oppdateres

Fiender (Vise fiender/monstre; de skal interagere med terreng og spiller):
- Som spiller ønsker jeg fiender/monstre som jeg må unngå for å få litt spenning/utfordring i spillet
    - Akseptansekriterier:
        - Spillmodellen har et fiendeobjekt
            - Kan være stasjonært, utvide med bevegelse etter hvert
        - Fienden vises på skjermen
        - Fienden har en tekstur og en body
        - Spiller kan ikke gå gjennom fiende
    - Arbeidsoppgaver:
        - Opprette et fiendeobjekt i modellen
        - Legg til fiende i tilemap
            - Pass på at fiende lastes inn
        - Legg til kontakt mellom spiller og fiende

Spillerdød (Spiller kan dø (ved kontakt med fiender, eller ved å falle utfor skjermen)):
- Som spiller ønsker jeg en fare for at spillet avsluttes slik at spillet blir mer interessant
    - Akseptansekriterier:
        - TODO
    - Arbeidsoppgaver:
        - TODO