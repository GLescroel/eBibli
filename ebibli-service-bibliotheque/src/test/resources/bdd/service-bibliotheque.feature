#Cette feature traite les fonctionnalités du micro-service bibliotheque
Feature: §Microservice - Gérer les Bibliotheques
  En tant client du microservice bibliotheque,
  Je veux pouvoir rechercher une ou toutes les bibliotheques
  Afin de pouvoir consulter les bibliotheques.

  Scenario Outline: recherche d'une bibliotheque par son id
    Given je recherche la bibliotheque d'"<id>"
    When j'interroge le microservice bibliotheque avec son id
    Then Les infos <rechercheBibliothequeResultat> de la bibliotheque du fichier rechercheBibliothequeResultat.json sont retournées
    Examples:
      | id  | rechercheBibliothequeResultat  |
      | 1 | rechercheBibliothequeResultat1 |
      | 2 | rechercheBibliothequeResultat2 |
      | 3 | rechercheBibliothequeResultat3 |

  Scenario Outline: recherche de toutes les bibliotheque
    Given je recherche les bibliotheques
    When j'interroge le microservice bibliotheque
    Then Les infos <rechercheBibliothequeResultat> de toutes les bibliotheques du fichier rechercheBibliothequeResultat.json sont retournées
    Examples:
      | rechercheBibliothequeResultat  |
      | rechercheBibliothequesResultat |
