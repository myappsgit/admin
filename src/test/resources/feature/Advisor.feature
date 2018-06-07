#Feature: Advisor
#
#    Background:
#        Given I am logged into the application with user "advisor@huddil.com" and password "password"
#        Then the "Advisor" page should load
#
#    @Sanity2
#    Scenario: Verify Advertiser main screen and options
#        When the following text should appear in the "Advisor Home" pane
#            | Dashboard   |
#            | Facility    |
#            | Booking     |
#            | Partners    |
#            | New Request |
#            | Approved    |
#            | Denied      |
#            | Blocked     |
#        And I wait "5" seconds
#        And I click the "UserProfile" element
#        Then the following text should appear in the "User-circle" pane
#            | Change Password |
#            | Logout          |
#
#    @Sanity
#    Scenario: Verify facility approve
#        And I wait "2" seconds
#        And I click the "Facility" element
#        And I click the "Filter" button
#        And I wait "5" seconds
#        And I select "Pending for approval" from the "SelectStatus" dropdown
#        And I wait "10" seconds
#        Then I click the "Search" button
#
##        Then the following text should appear in the "Facility Listing" pane
##            |conference 1|
#        And I select the facility "training 456"
#        And I click the "Approve" button
#        And I click the "OK1" button
#        And I click the "Verify" button
#        And I click the "OK1" button
#
#
#    @Sanity
#    Scenario:Verify facility deny
#        And I click the "Booking" element
#        And I click the "Facility" element
#        And I click the "Filter" button
#        And I select "Pending for approval" from the "SelectStatus" dropdown
#        Then I click the "Search" button
#        And I wait "10" seconds
#        And I select the facility "meeting room"
#        And I click the "Deny" button
#        And I enter "Test" in the "Comments" field
#        And I click the "Update" button
#        And I click the "OK1" button
#
#    @Sanity
#    Scenario: Verify Facility Block and unblock
#        And I click the "Booking" element
#        And I click the "Facility" element
#        And I click the "Filter" button
#        And I select "Approved" from the "SelectStatus" dropdown
#        Then I click the "Search" button
#        And I wait "15" seconds
#        And I select the facility "training 456"
#        And I click the "Block" button
#        And I enter "Test" in the "Comments" field
#        And I click the "Update" button
#        Then I click the "OK1" button
#
#    @Sanity
#    Scenario: Verify Facility Block and unblock
#        And I click the "Booking" element
#        And I click the "Facility" element
#        And I click the "Filter" button
#        And I select "Approved" from the "SelectStatus" dropdown
#        Then I click the "Search" button
#        And I wait "15" seconds
#        And I select the facility "training 456"
#        And I wait "2" seconds
#        And I click the "Unblock" button
#        Then I click the "OK1" button
#
#    @Sanity
#    Scenario: Verify Facility filter with facility type
#        And I click the "Booking" element
#        When I click the "Facility" element
#        And I click the "Filter" button
#        Then I select "Training Room" from the "Select Facility Type" dropdown
##        Then I click the "Search" button
##        And I wait "3" seconds
##        Then the following text should appear in the "Facility Listing" pane
##            | training 11   |
##         @Sanity
##        Scenario: Filter the facility based on City
##          When I click the "Facility" element
##            And I click the "Filter" button
#        And I select "Bangalore" from the "Select City" dropdown
#        And I wait "5" seconds
#        And I select "Bannerghatta Road" from the "Select Locality" dropdown
#        And I select "Approved" from the "SelectStatus" dropdown
#        Then I click the "Search" button
#        And I wait "10" seconds
#        Then the following text should appear in the "Facility Listing" pane
#            | training 456 |
#
#    @Sanity
#    Scenario:Search the facility with facility title
#        And I click the "Booking" element
#        Given I click the "Facility" element
#        And I enter "training 456" in the "Search Field" field
#        Then I click the "Search" button
#        And I wait "5" seconds
#        Then the following text should appear in the "Facility Listing" pane
#            | training 456 |
#
#    @Sanity
#    Scenario: Search booking with booking filters
#        And I click the "Booking" element
##       And I enter "@gmail.com" in the "Booking Search" field
#        Then I click the "Search1" button
#        Then the following text should appear in the "BookingDetailsDashboard" pane
#            | Room Name |
#            | Address   |
#        And I click the "Booking Filter" button
#        And I select "Confirmed" from the "BookingStatus" dropdown
#        Then I click the "Search1" button
#        Then the following text should appear in the "BookingDetailsDashboard" pane
#            | Confirmed |
#
#    @Sanity
#    Scenario: Filter booking details with select facility type ,City ,Loclity
#        And I click the "Partners" element
#        And I click the "Booking" element
#        Given I click the "Booking Filter" button
#        And I select "Conference Room" from the "Select Booking Facility Type" dropdown
#        And I select "Bangalore" from the "BookingSelectCity" dropdown
#        And I wait "2" seconds
#        And I select "Bannerghatta Road" from the "Select Locality" dropdown
#        Then I click the "Search1" button
#        And I wait "2" seconds
#        Then the following text should appear in the "BookingDetailsDashboard" pane
#            | Booking Id   |
#            | Payment Mode |
#            | Address      |
#
#    @Sanity
#    Scenario:Partners dashboard verify
#        And I click the "Partners" element
#        And I click the "Search" button
#        Then the following text should appear in the "partners" pane
#            | User Name     |
#            | Email Id      |
#            | Mobile Number |
#            | View          |
#        And I view the user details of "aparna"
#        Then the following text should appear in the "Serviceprovidersdetails" pane
#            | Owner Name    |
#            | Email Id      |
#            | Mobile Number |
#        And I click the "Block" button
#        And I enter "Test" in the "Comments" field
#        Then I click the "Update" button
#        And I click the "OK" button
#        Then I enter "aparna" in the "SearchField" field
#        And I click the "Search" button
#        And I click the "View" element
#        And I click the "UnBlock" button
#        And I click the "OK" button
#
#    @Sanity
#    Scenario:Change password
#        And I wait "3" seconds
#        And I click the "User Profile" element
#        And I click the "ChangePassword" element
#        And I enter "password" in the "OldPassword" field
#        And I enter "password1" in the "NewPassword" field
#        And I enter "password1" in the "ConfirmPassword" field
#        And I click the "SaveChanges" button
#        And I wait "3" seconds
#        And I click the "OkButton" button
#        And I click the "User Profile" element
#        And I click the "Logout" element