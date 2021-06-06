package com.lmsu.bean.member;

import java.io.Serializable;

public class BookObj implements Serializable {
        private String id;
        private String title;
        private String authorName;
        private String subjectName;
        private String publisher;
        private String publishDate;
        private String description;
        private int quantity;
        private float avgRating;
        private String isbnTen;
        private String isbnThirteen;
        private String coverPath;

        public BookObj(String id, String title, String authorName, String subjectName, String publisher,
                       String publishDate, String description, int quantity, float avgRating, String isbnTen,
                       String isbnThirteen, String coverPath) {
                this.id = id;
                this.title = title;
                this.authorName = authorName;
                this.subjectName = subjectName;
                this.publisher = publisher;
                this.publishDate = publishDate;
                this.description = description;
                this.quantity = quantity;
                this.avgRating = avgRating;
                this.isbnTen = isbnTen;
                this.isbnThirteen = isbnThirteen;
                this.coverPath = coverPath;
        }

        public String getId() {
                return id;
        }

        public String getTitle() {
                return title;
        }

        public String getAuthorName() {
                return authorName;
        }

        public String getSubjectName() {
                return subjectName;
        }

        public String getPublisher() {
                return publisher;
        }

        public String getPublishDate() {
                return publishDate;
        }

        public String getDescription() {
                return description;
        }

        public int getQuantity() { return quantity; }

        public float getAvgRating() {
                return avgRating;
        }

        public String getIsbnTen() {
                return isbnTen;
        }

        public String getIsbnThirteen() {
                return isbnThirteen;
        }

        public String getCoverPath() {
                return coverPath;
        }
}
