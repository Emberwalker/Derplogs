# TODO list
## POST parsing
- Check signatures (`X-Hub-Signature` header) against the PubSubAgent (`psAgent ? SignatureCheck(msg, hash)`)
    - Strip `sha1=` from the start of `hash` somewhere
- Remove commits with `"distinct": false` (nobody likes a repeat)
- Ignore merge commits somehow (those aren't interesting to anyone; Git Flow users cause a LOT of these \*shakes fist\*)
    - Look for the general pattern `Merge branch [...]` maybe?
- Figure where to get avatars with the least API queries
- Sanitise to UTF-8
    - This is just common sense really.
- Remove excess newlines (max. 2 in a row?)

## Integrations
- Yo. Nuff said.
    - Yo Link to GH repo
        - Success? Send link back to verify location (Yo Location to confirm).
            - Oh god location fuzzy matching whyyyy
        - Fail? Send link to RTFM page.

## Misc.
- Buttbot-esque Markov Chaining or something silly to that effect?
- Configurable 'censoring'? (e.g. censor 'Windows' (Angus would approve))