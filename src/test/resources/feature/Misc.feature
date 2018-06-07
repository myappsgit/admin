#Feature: Misc
#    This feature contains the flow validation between users, Login/Logout,
#    Forgot Password and Sign Up scenarios
#
#    Scenario: Verify Forgot Password link with valid email
#        Given I am on the "Login" page
#        When I click the "Signin/Signup" element
#        And I click the "Forgot Your Password? Get Help" link
#        And I enter "testuser@test.com" in the "Password Recovery Email" field
#        Then I click the "Send My Password" button
#        And the message "Reset link Successfully sent on your registered email id." should be displayed
#
#    Scenario: Verify Forgot Password link with invalid email
#        Given I am on the "Login" page
#        When I click the "Signin/Signup" element
#        And I click the "Forgot Your Password? Get Help" link
#        And I enter "email" in the "Password Recovery Email" field
#        Then the following text should appear in the "Password Recovery" pane
#            | Enter Valid Email ID |
#        And I enter "email@dom.com" in the "Password Recovery Email" field
#        And I click the "Send My Password" button
#        And the message "Email Id Not Exist in Our System" should be displayed
#
#    @Sanity
#    Scenario: Verify Edit Profile
#        When I click the "Edit Profile" button
#        And I enter "Swetha" in the "Username" field
#        And I enter "8884530882" in the "Phone Number" field
#        And I click the "Send OTP" button
#        Then the following elements should be displayed
#            | Name                        | Type      |
#            | Con Enter OTP               | textfield |
#            | Con Enter new mobile number | textfield |
#        And I enter "568134" in the "Enter OTP" field
#        And I enter "8884530882" in the "Enter new mobile number" field
#        And I click the "Save Changes" button
#        And the message "Mobile Number Updated Successfully" should be displayed
#
#    @Sanity
#    Scenario: Change Password
#        When I click the "Change Password" button
#        And I enter "password" in the "Old Password" field
#        And I enter "password1" in the "New Password" field
#        And I enter "password1" in the "Confirm Password" field
#        And I click the "Save Changes" button
#        Then the message "Password changed successfully" should be displayed
#
#    @Sanity
#    Scenario: Verify Service Provider Sign Up
#        Given I launch the "Huddil" web application
#        And I click the "Partner With Us" button
#        And I enter "aparna" in the "SP Owner Name" field
#        And I enter "password" in the "SP Password" field
#        And I enter "password" in the "SP Conf Password" field
#        And I enter "8497995311" in the "SP Phone" field
#        And I enter "aparna.b@myapps-solutions.com" in the "SP Email" field
#        And I enter "myappssolutions" in the "SP Website" field
#        And I enter "bangalore" in the "SP Address" field
#        And I enter "560037" in the "SP Pincode" field
#        And I enter "marathalli" in the "SP City" field
#        And I select "Andhra Pradesh" from the "SPState" dropdown
#        And I enter "india" in the "SP Country" field
#        And I check the "Sign Up Terms" checkbox
#        And I click the "SP Sign Up Submit" button
#        And I wait "10" seconds
#        Then the following elements should be displayed
#            | Name          | Type      |
#            | Con Enter OTP | textfield |
#        Then the element "Con Enter OTP" should be displayed
#
#    @Sanity
#    Scenario: Verify facebook login fuctionality
#        Given I launch the "Huddil" web application
#        And I click the "Login" button
#        And I click the "Facebook Login" element
#
#    @Sanity
#    Scenario: Verify forgot password functionality
#        Given I launch the "Huddil" web application
#        And I click the "Login" button
#        And I click the "Forgot Password" element
#        And I enter "aparna.b@myapps-solutions.com" in the "Registered Email" field
#        And I click the "Reset My Password" button
#
