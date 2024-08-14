package com.example.library.controller;

import com.example.library.model.Book;
import com.example.library.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @Operation(summary = "Get a book by title and author")
    @GetMapping("/search")
    public ResponseEntity<Optional<Book>> getBookByTitleAndAuthor(
            @Parameter(description = "Название книги", required = true) @RequestParam String title,
            @Parameter(description = "Автор книги", required = true) @RequestParam String author) {
        Optional<Book> book = bookService.findByTitleAndAuthor(title, author);
        return book.isPresent() ? ResponseEntity.ok(book) : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Get a list of books by category name")
    @GetMapping("/category/{categoryName}")
    public Set<Book> getBooksByCategoryName(
            @Parameter(description = "Имя категории", required = true) @PathVariable String categoryName) {
        return bookService.findByCategoryName(categoryName);
    }

    @Operation(summary = "Create a new book")
    @PostMapping
    public Book createBook(
            @Parameter(description = "Название книги", required = true) @RequestParam String title,
            @Parameter(description = "Автор книги", required = true) @RequestParam String author,
            @Parameter(description = "Имя категории", required = true) @RequestParam String categoryName) {
        return bookService.createBook(title, author, categoryName);
    }

    @Operation(summary = "Update an existing book")
    @PutMapping("/{id}")
    public ResponseEntity<Optional<Book>> updateBook(
            @Parameter(description = "ID книги", required = true) @PathVariable Long id,
            @Parameter(description = "Название книги", required = true) @RequestParam String title,
            @Parameter(description = "Автор книги", required = true) @RequestParam String author,
            @Parameter(description = "Имя категории", required = true) @RequestParam String categoryName) {
        Optional<Book> updatedBook = bookService.updateBook(id, title, author, categoryName);
        return updatedBook.isPresent() ? ResponseEntity.ok(updatedBook) : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Delete a book by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(
            @Parameter(description = "ID книги", required = true) @PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}