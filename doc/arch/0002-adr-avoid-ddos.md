# 2. adr avoid ddos

Date: 2021-07-19

## Status

Accepted

## Context

Then shorten application expose API without any limitation, everyone can call it.

## Decision

Applying rating limit to limit the request per identifier (IP/account).

## Consequences

