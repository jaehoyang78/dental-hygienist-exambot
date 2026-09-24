#!/usr/bin/env bash
set -euo pipefail
root="$(cd "$(dirname "$0")/.." && pwd)"
cp "$root/index.html" "$root/docs/index.html"
cp "$root/questions.js" "$root/docs/questions.js"
cp "$root/index.html" "$root/app/src/main/assets/index.html"
cp "$root/questions.js" "$root/app/src/main/assets/questions.js"
