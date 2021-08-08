# 4. adr build high available app

Date: 2021-07-19

## Status

Accepted

## Context

How Do We handle application increase server die/interrupt?

## Decision

Using the nginx upstream to archive this. one server go live and one server for backup.

## Consequences

