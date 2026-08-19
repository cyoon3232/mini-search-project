# Architecture

## Overview
The system indexes documents and allows users to search the indexed documents using normalized queries. Search results show matched documents ranked by relevance.

## Components
Document - represents a document for search containing an ID, title, and raw text content  
Tokenizer - converts raw document text into normalized, ordered tokens for search  
InvertedIndex - stores and retrieves term's occurrences by document and position  
SearchEngine - coordinates indexing and searching using the tokenizer, inverted index, and document map   
SearchResult - represents search result showing documentID and the documents are ranked by their relevance score  

## Current Data Flow
Saving:  
Document -> SearchEngine   
    -> Document map  
    -> Tokenizer -> InvertedIndex
Searching:  
Query -> SearchEngine -> Tokenizer / Query processing -> InvertedIndex -> Ranking -> SearchResult  