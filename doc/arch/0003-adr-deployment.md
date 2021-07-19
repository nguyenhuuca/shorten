# 3. adr deployment

Date: 2021-07-19

## Status

Accepted

## Context

Need to deploy application with zero downtime

## Decision

Because the application using database with little table, using the blue-green deployment to archive zero-downtime,
In the future, Maybe apply the another way in-case the database need to migration often.

## Consequences

