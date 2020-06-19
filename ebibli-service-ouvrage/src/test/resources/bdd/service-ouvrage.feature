#Cette feature traite les fonctionnalités du micro-service ouvrage
Feature: §Microservice - Gérer les ouvrages
  En tant client du microservice ouvrage,
  Je veux pouvoir rechercher un ou tous les ouvrages
  Afin de pouvoir consulter les ouvrages.

  Scenario Outline: recherche d'un ouvrage par son id
    Given je recherche l'ouvrage d'"<id>"
    When j'interroge le microservice ouvrage avec son id
    Then Les infos <rechercheOuvrageResultat> de l'ouvrage du fichier rechercheOuvrage.json sont retournées
    Examples:
      | id | rechercheOuvrageResultat  |
      | 2  | rechercheOuvrageResultat1 |
      | 15 | rechercheOuvrageResultat2 |

  Scenario Outline: recherche de tous les ouvrages
    Given je recherche tous les ouvrages
    When j'interroge le microservice ouvrage
    Then Les infos <rechercheAllOuvragesResultat> de tous les ouvrages du fichier json sont retournées
    Examples:
      | rechercheAllOuvragesResultat |
      | rechercheAllOuvragesResultat |
