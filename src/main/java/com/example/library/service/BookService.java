package com.example.library.service;

import com.example.library.model.Book;
import com.example.library.model.Category;
import com.example.library.repository.BookRepository;
import com.example.library.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.Set;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Cacheable(value = "books", key = "#title + '-' + #author")
    public Optional<Book> findByTitleAndAuthor(String title, String author) {
        return bookRepository.findByTitleAndAuthor(title, author);
    }

    @Cacheable(value = "booksByCategory", key = "#categoryName")
    public Set<Book> findByCategoryName(String categoryName) {
        return bookRepository.findByCategoryName(categoryName);
    }

    @CacheEvict(value = {"books", "booksByCategory"}, allEntries = true)
    public com.example.library.model.Book createBook(String title, String author, String categoryName) {
        Category category = categoryRepository.findByName(categoryName)
                .orElse(new Category(categoryName));

        com.example.library.model.Book book = new com.example.library.model.Book(title, author, category);
        category.getBooks().add(book);

        categoryRepository.save(category);
        return bookRepository.save(book);
    }

    @CacheEvict(value = {"books", "booksByCategory"}, allEntries = true)
    public Optional<Book> updateBook(Long id, String title, String author, String categoryName) {
        return bookRepository.findById(id).map(existingBook -> {
            existingBook.setTitle(title);
            existingBook.setAuthor(author);

            Category category = categoryRepository.findByName(categoryName)
                    .orElse(new Category(categoryName));
            existingBook.setCategory(category);

            category.getBooks().add(existingBook);

            categoryRepository.save(category);
            return bookRepository.save(existingBook);
        });
    }

    @CacheEvict(value = {"books", "booksByCategory"}, allEntries = true)
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }
}
