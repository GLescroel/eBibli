#Cette feature traite les fonctionnalités du micro-service utilisateur
Feature: §Microservice - Gérer les utilisateurs
  En tant client du microservice utilisateur,
  Je veux pouvoir créer, rechercher, supprimer les utilisateurs
  Afin de pouvoir gérer les utilisateurs.

  Scenario Outline: recherche d'un utilisateur par son email
    Given je recherche l'utilisateur par son "<email>"
    When j'interroge le microservice utilisateur avec son email
    Then Les infos <rechercheUtilisateurResultat> de l'utilisateur du fichier rechercheUtilisateurResultat.json sont retournées
    Examples:
      | email  | rechercheUtilisateurResultat  |
      | glescroel@hotmail.com | rechercheUtilisateurResultat1 |
      | gregory.lescroel@axa.fr | rechercheUtilisateurResultat2 |
      | admin@oc.com | rechercheUtilisateurResultat3 |
      | smith@oc.com | rechercheUtilisateurResultat4 |

  Scenario Outline: recherche d'un utilisateur par son id
    Given je recherche l'utilisateur d'"<id>"
    When j'interroge le microservice utilisateur avec son id
    Then Les infos <rechercheUtilisateurResultat> de l'utilisateur du fichier rechercheUtilisateurResultat.json sont retournées
    Examples:
      | id  | rechercheUtilisateurResultat  |
      | 4 | rechercheUtilisateurResultat1 |
      | 8 | rechercheUtilisateurResultat2 |
      | 1 | rechercheUtilisateurResultat3 |
      | 7 | rechercheUtilisateurResultat4 |

  Scenario Outline: recherche de tous les utilisateurs
    Given je recherche tous les utilisateurs
    When j'interroge le microservice utilisateur
    Then Les infos <rechercheUtilisateurResultat> de tous les utilisateurs du fichier rechercheUtilisateurResultat.json sont retournées
    Examples:
      | rechercheUtilisateurResultat  |
      | rechercheUtilisateursResultat |

  Scenario Outline: cycle de vie d'un utilisateur
    Given je crée l'utilisateur suivant
      | <nom> | <prenom> | <email> | <password> | <role_id> | <role> |
    And Je constate que l'utilisateur existe bien avec les données suivantes
      | <nom> | <prenom> | <email> | <password> | <role_id> | <role> |
    When Je supprime l'utilisateur
    Then L'utilisateur n'existe plus dans la base
    Examples:
      | nom   | prenom | email       | password | role_id | role    |
      | SMITH | JOHN   | SMITH@OC.FR | 123456   |     4   | Employe |
