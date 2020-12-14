package com.example.houseofhope.api.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GuestModel {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("guest_name")
        @Expose
        private String guestName;
        @SerializedName("guest_car")
        @Expose
        private String guestCar;
        @SerializedName("visit_dong")
        @Expose
        private String visitDong;
        @SerializedName("visit_ho")
        @Expose
        private String visitHo;
        @SerializedName("visit_why")
        @Expose
        private String visitWhy;
        @SerializedName("visit_day")
        @Expose
        private String visitDay;
        @SerializedName("status")
        @Expose
        private Integer status;
        @SerializedName("count_picture")
        @Expose
        private Integer countPicture;
        @SerializedName("published_at")
        @Expose
        private String publishedAt;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("train_face")
        @Expose
        private Boolean trainFace;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getGuestName() {
            return guestName;
        }

        public void setGuestName(String guestName) {
            this.guestName = guestName;
        }

        public String getGuestCar() {
            return guestCar;
        }

        public void setGuestCar(String guestCar) {
            this.guestCar = guestCar;
        }

        public String getVisitDong() {
            return visitDong;
        }

        public void setVisitDong(String visitDong) {
            this.visitDong = visitDong;
        }

        public String getVisitHo() {
            return visitHo;
        }

        public void setVisitHo(String visitHo) {
            this.visitHo = visitHo;
        }

        public String getVisitWhy() {
            return visitWhy;
        }

        public void setVisitWhy(String visitWhy) {
            this.visitWhy = visitWhy;
        }

        public String getVisitDay() {
            return visitDay;
        }

        public void setVisitDay(String visitDay) {
            this.visitDay = visitDay;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public Integer getCountPicture() {
            return countPicture;
        }

        public void setCountPicture(Integer countPicture) {
            this.countPicture = countPicture;
        }

        public String getPublishedAt() {
            return publishedAt;
        }

        public void setPublishedAt(String publishedAt) {
            this.publishedAt = publishedAt;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public Boolean getTrainFace() {
            return trainFace;
        }

        public void setTrainFace(Boolean trainFace) {
            this.trainFace = trainFace;
        }
}
