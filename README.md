# Java Search Platform

A search engine built from scratch in Java to explore information retrieval, data stuctures, and backend architecture.

The current version supports document indexing, text tokenization, positional inverted indexing, multi-term retrieval, and frequency-based relevance ranking. and an interactive command-lin demo.

It was intentionally built without search frameworks such as Lucene or Elasticsearch so the algorithm and indexing could be understood directly.

### Features
Currently,
- Document creation & validation
- Text tokenization & normalization
- Positional inverted index
- Multi-term search
- Query matching (distinct query terms with no ranking yet)
- Term-frequency-based relevance scoring of Documents
- Interactive command-line demo

---

## Demo

The project includes an interactive command-line demo. This demo was created with the help of ChatGPT and Claude.

Users can:
1. Add new documents
2. Search indexed through indexed documents
3. List currently indexed documents
4. Exit the application

When adding a document, the user must provide:
- Document ID (non-negative & unique)
- Document Title (non-blank)
- Document Content

When searching, the user must not put blank as a query term.

---

## How It Works

**Search and Ranking**
With a non-null, non-blank query term, the algorithm first deduplicates it. Then it finds each term in the map, sums the occurrence (list size of positions) in each matched document.  
This is the baseline ranking implementation. TF-IDF/BM25 is planned.

## Data Structures
Positional inverted index term -> document ID -> token positions implemented as `Map<String, Map<Integer, List<Integer>>>`  

- Outer HashMap: average O(1) term search
- Inner HashMap: average O(1) document search
- ArrayList: average O(1) append term position, average O(n) traversal

## Architecture
TODO: Diagram
