#!/bin/bash

docker-compose up -d postgres app
docker-compose logs -f app