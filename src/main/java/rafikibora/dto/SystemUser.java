package rafikibora.dto;

import rafikibora.model.users.User;

public class SystemUser {
        private String firstName;
        private String lastName;
        private String email;
        private String phoneNo;
        private String createdBy;
        private String approvedBy;
        private String userId;
        private String username;
        private String mid;
        private String businessName;

        public SystemUser(User user){
                this.firstName = user.getFirstName();
                this.lastName = user.getLastName();
                this.email = user.getEmail();
                this.phoneNo = user.getPhoneNo();
                if(user.getUserMaker() != null)
                        this.createdBy = user.getUserMaker().getEmail();
                else this.createdBy = "SYSTEM ADMIN";

                if(user.getUserChecker() != null)
                        this.approvedBy = user.getUserChecker().getEmail();
                else
                        this.approvedBy = "UNAPPROVED";

                this.userId = String.valueOf(user.getUserid());
                this.username = user.getUsername();

                if(user.getMid() != null)
                        this.mid = user.getMid();
                else
                        this.mid = "NOT A MERCHANT";

                if(user.getBusinessName() != null)
                        this.businessName = user.getBusinessName();
                else
                        this.businessName = "NOT A BUSINESS";

        }

        public String getFirstName() {
                return firstName;
        }

        public String getLastName() {
                return lastName;
        }

        public String getEmail() {
                return email;
        }

        public String getPhoneNo() {
                return phoneNo;
        }

        public String getCreatedBy() {
                return createdBy;
        }

        public String getApprovedBy() {
                return approvedBy;
        }

        public String getUserId() {
                return userId;
        }

        public String getUsername() {
                return username;
        }

        public String getMid() {
                return mid;
        }

        public String getBusinessName() {
                return businessName;
        }
}
