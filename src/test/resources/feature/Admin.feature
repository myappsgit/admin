Feature: Admin

    Background:
        Given I am logged into the application with user "admin@huddil.com" and password "password"
        And the "Admin" page should load

    @Sanity
    Scenario: Verify Payment Status on Dashboard
        And I select "2017" from the "SelectYear" dropdown
        And I select "March" from the "SelectMonth" dropdown

    @Sanity
    Scenario: Verify Payment Details using search in Payments
        When I click the "Payments" element
        And I select "February" from the "SelectMonth" dropdown
        And I select "2018" from the "SelectYear" dropdown
        And I select "Bangalore" from the "AllCities" dropdown
        And I enter "aparna.b@myapps-solutions.com" in the "ServiceProvider" field
        And I click the "PaymentsSearch" button

    @Sanity
    Scenario: Verify Search & Block Users in Users
        When I click the "Users" element
        And I enter "santhoshkumarthota93@gmail.com" in the "NameMail-id" field
        And I select "Service Provider" from the "selectUserType" dropdown
        And I click the "Find" button
        And I click the "ActiveBlock" button

    @Sanity
    Scenario: Verify Service Provider Details view in Users
        When I click the "Users" element
        And I enter "santhoshkumarthota93@gmail.com" in the "NameMail-id" field
        And I select "Service Provider" from the "selectUserType" dropdown
        And I click the "Find" button
        And I click the "santhosh" element
        And I click the "Close" button

    @Sanity
    Scenario: Verify Service Provider Commission update
        When I click the "Users" element
        And I enter "santhoshkumarthota93@gmail.com" in the "NameMail-id" field
        And I select "Service Provider" from the "selectUserType" dropdown
        And I click the "Find" button
        And I enter "20" in the "NextMonth" field
        And I click the "SaveCommission" button
        And I click the "OK2" button

    @Sanity
    Scenario: Verify Adding "New Adviser"
        When I click the "Users" element
        And I click the "NewAdviser" button
        And I enter "Rajchinna" in the "AdviserName" field
        And I enter "myappssolutions997@yahoo.in" in the "AdviserEmail" field
        And I enter "9704687436" in the "AdviserMobileNo" field
        And I enter "Raj@123456" in the "AdviserPWD" field
        And I click the "Save" button
        And I wait "3" seconds
        And I click the "OK2" button

    @Sanity
    Scenario: Verify Search Facility
        When I click the "Facilities" element
        And I enter "a" in the "SearchFacility" field
        And I select "Locality" from the "SearchFor" dropdown
        And I wait "5" seconds
        And I select "Conference Room" from the "searchType" dropdown
        And I click the "SearchButton" button
        And I wait "3" seconds


    @Sanity
    Scenario: Verify Search & Block Facility
        When I click the "Facilities" element
        And I enter "a" in the "SearchFacility" field
        And I select "Locality" from the "SearchFor" dropdown
        And I wait "5" seconds
        And I select "Conference Room" from the "searchType" dropdown
        And I click the "SearchButton" button
        And I wait "10" seconds
        And I select the facility "Confo_BTM"
        And I wait "3" seconds
        And I click the "BlockUnblock" button
        And I click the "OK2" button
        And I wait "2" seconds

    @Sanity
    Scenario: Verify Adding Policy
        When I click the "Terms" element
        And I click the "+AddPolicy" button
        And I enter "2018-06-05" in the "ActiveDate" field
        And I select "Service Provider" from the "UserType" dropdown
#        And I click the "FileUpload" button
        And I wait "15" seconds
#        And I upload the "chart" file
#        And I wait "10" seconds
        And I click the "Save" button
        And I click the "OK2" button

    @Sanity
    Scenario: Verify Adding Amenity
        When I click the "Amenities" element
        And I click the "AddAmenity" button
        And I wait "15" seconds
#        Then I enter value in the text field "Svg Text" from text file "fileread"
        And I enter "Test" in the "AmenityName" field
        And I click the "Save" button
        And I click the "OK2" button

    @Sanity
    Scenario: Verify Adding City
        When I click the "Locations" element
        And I click the "AddCity" button
        And I enter "Jaipur" in the "CityName" field
        And I click the "Save" button
        And I click the "OK2" button

    @Sanity
    Scenario: Verify Adding Locality
        When I click the "Locations" element
        And I click the "Jaipur" element
        And I click the "AddLocality" button
        And I enter "RoadNo1" in the "EnterLocality" field
        And I click the "LocalitiesSave" button
        And I click the "OK2" button

    @Sanity
    Scenario: Verify Reports
        When I click the "Reports" element
        And I wait "5" seconds


    @Sanity
    Scenario: Verify Adding Offers
        When I click the "Offers" element
        And I enter "2018-09-20 10:00:00" in the "FromOfferDate" field
        And I enter "2018-09-21 18:00:00" in the "To Offer Date" field
        And I enter "10" in the "Huddil Offer" field
        And I click the "Add Offer" button


    @Sanity
    Scenario: Verify Edit Admin Name
        When I click the "UserProfile" element
        And I click the "EditProfile" element
        And I enter "Huddil Admin" in the "Name" field
        And I click the "Update" button
        And I click the "OK2" button


    @Sanity
    Scenario: Verify Change password
    And I click the "UserProfile" element
    Then the following text should appear in the "UserProfile" pane
      | Edit Profile |
      | Change Password |
      | Logout         |
    And I click the "ChangePassword" element
    And I enter "password1" in the "OldPassword" field
    And I enter "password" in the "NewPassword" field
    And I enter "password" in the "ConfirmPassword" field
    And I click the "SaveChanges" button
    And I click the "OK2" button

    @Sanity
    Scenario: Verify Logout
        When I click the "UserProfile" element
        And I click the "Logout" element
        And I click the "OK2" button





