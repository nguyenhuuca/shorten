# 1. adr message queue

Date: 2021-07-19

## Status

Accepted

## Context
The application need to run async thread to store the url after shorten link for high performance

## Decision

Applying the pub-sub model, using LMAX disruptor 

## Consequences

