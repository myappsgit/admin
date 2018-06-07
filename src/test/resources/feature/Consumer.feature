#Feature: Consumer
#
#    Background:
#        Given I am logged into the application with user "swetha.r@myapps-solutions.com" and password "password1"
#        Then the "Consumer" page should load
#
#    @Sanity1
#    Scenario: Verify Search Options in Search Page
#        When I select "Bangalore" from the "Select City" dropdown
#        And I select "Bannerghatta Road" from the "Select Locality" dropdown
#        And I select "Co-Working Space" from the "Select Facility Type" dropdown
#        And I click the "submit" button
#        And I select "Meeting Room" from the "Select Facility Type" dropdown
#        And I click the "submit" button
##    And I wait "5" seconds
##    And I click the "UserProfile" element
##       And I click the "Logout" element
#
#
#    @Sanity1
#    Scenario: Verify Filter Options in Search Page
#        When I select "Co-Working Space" from the "Select Facility Type" dropdown
#        And I click the "submit" button
#        Then I click the "Filter" button
#        Then I enter "30" in the "Minpriceperday " field
# #    Then I enter "30" in the "Maxpriceperday" field
# #    Then I enter ".*" in the "Seats1" field
# #    Then I enter ".*" in the "StartDate" field
# #    Then I enter ".*" in the "Enddate" field
#        Then I select "Projector" from the "SelectAmenities" dropdown
#        And I click the "submit" button
#        And I wait "3" seconds
#
#
#    @Sanity1
#    Scenario: Verify Add Attendees
#        And I click the "UserProfile" element
#        When I click the "Attendees" element
#        And I click the "Add Team" button
#        And I enter "Interview Team" in the "Team Name" field
#        And I enter "Rajashekar" in the "Name*" field
#        And I enter "9704636247" in the "Mobile" field
#        And I enter "rajashekar.b@myapps-solutions.com" in the "E-mail" field
#        And I click the "Save" button
#        Then the message "Team added successfully" should be displayed
#        And I click the "OK" button
#
#
#    @Sanity
#    Scenario: Verify Edit Attendees
#        And I click the "UserProfile" element
#        When I click the "Attendees" element
#        And I click the "EditAttendee" element
# #    Then the following elements should be displayed
# #      | Team Name  |
# #      | Name       |
# #      | Mobile     |
# #      | E-mail     |
#        Then I click the "Add New Attendee" link
#        And I enter "swetha" in the "Name1" field
#        And I enter "8884530882" in the "Mobile1" field
#        And I enter "swetha.r@myapps-solutions.com" in the "E-mail1" field
#        And I click the "Update" button
#        Then the message "Team details updated successfully." should be displayed
#        And I click the "OK" button
#
#
#    @Sanity
#    Scenario: Verify Edit Profile_Name Change
#        When I click the "UserProfile" element
#        And I click the "EditProfile" element
#        And I enter "Swethu" in the "Name" field
#        And I click the "SaveChanges" button
#        Then the message "User updated successfully" should be displayed
#        Then I click the "OK" button
#
#
#    @Sanity
#    Scenario: Verify Change Password
#        And I click the "UserProfile" element
#        And I click the "ChangePassword" element
#        And I enter "password" in the "OldPassword" field
#        And I enter "password1" in the "NewPassword" field
#        And I enter "password1" in the "ConfirmPassword" field
#        And I click the "SaveChanges" button
#
#
#    @Sanity
#    Scenario: Verify Change Password with Old Password-Invalid
#        And I click the "UserProfile" element
#        And I click the "ChangePassword" element
#        And I enter "password" in the "OldPassword" field
#        And I enter "password1" in the "NewPassword" field
#        And I enter "password1" in the "ConfirmPassword" field
#        And I click the "SaveChanges" button
#
#
#    @Sanity
#    Scenario: Verify Add & Remove Favorites
#        When I select "Conference Room" from the "SelectFacilityType" dropdown
#        And I click the "Facility Search" button
#        And I wait "8" seconds
#        Then I select the facility "conf 1"
#        And I wait "2" seconds
#        Then I click the "IsFavourite" element
#        Then the message "Favorities added successfully" should be displayed
#        And I click the "OK" button
#        And I wait "2" seconds
#        Then I click the "IsFavouriteRemove" element
#        Then the message "Favorities removed successfully." should be displayed
#        And I click the "OK" button
#
#    @Sanity
#    Scenario: Verify Book a Coworking Facility with Valid Timings & No. of seats
#        When I select "Co-Working Space" from the "Select Facility Type" dropdown
#        And I click the "Facility Search" button
#        And I wait "6" seconds
#        Then I select the facility "co-work"
#        And I enter "2018-02-26 10:00:00" in the "Start date" field
#        And I enter "2018-02-26 11:00:00" in the "End Date" field
#        And I enter "1" in the "Seats" field
#        And I click the "BookNow" button
#        And I click the "Confirm" button
##    Then the following elements should be displayed
##      | Your Co-Working Space has been Booked  |
##      | Booking Id:5                           |
##      | Add Meeting Details                    |
#        And I wait "3" seconds
#        And I click the "AddMeetingDetails" button
#        And I enter "Interview Team" in the "MeetingTitle" field
#        And I enter "Interview Discussion" in the "MeetingDescription" field
#        And I select "Interview Team" from the "team" dropdown
#        And I click the "Save" button
#        And I wait "3" seconds
#        And I click the "AddMeetingDetails" button
#        And I enter "Interview Team" in the "MeetingTitle" field
#        And I enter "Interview Discussion" in the "MeetingDescription" field
#        And I select "Interview Team" from the "team" dropdown
#        And I click the "Save" button
#        And I click "OK" in the confirmation box
#
#
#    @Sanity
#    Scenario: Verify Book a Coworking Facility with InValid Timings
#        When I select "Co-Working Space" from the "Select Facility Type" dropdown
#        And I click the "Facility Search" button
#        And I wait "6" seconds
#        Then I select the facility "co-work"
#        And I enter "2018-02-26 10:00:00" in the "Start date" field
#        And I enter "2018-02-26 11:00:00" in the "End Date" field
#
#
#    @Sanity
#    Scenario: Verify Book a Coworking Facility with Invalid No. of seats
#        When I select "Co-Working Space" from the "Select Facility Type" dropdown
#        And I click the "Facility Search" button
#        And I wait "6" seconds
#        Then I select the facility "Co-work1"
#        And I enter "2018-02-21 10:05:00" in the "Start date" field
#        And I enter "2018-02-21 10:10:00" in the "End Date" field
#        And I enter "4" in the "Seats" field
#
#
#    @Sanity
#    Scenario: Verify Valid Start Time & End Time-> Booking a Conference Room Offline
#        When I select "Conference Room" from the "SelectFacilityType" dropdown
#        And I click the "Facility Search" button
#        And I wait "8" seconds
#        Then I select the facility "conf 1"
#        And I enter "2018-02-26 10:00:00" in the "Start date" field
#        And I enter "2018-02-26 11:00:00" in the "End Date" field
#        And I click the "Start date" element
#        And I click the "BookNow" button
#        And I select "offline" from the "Payment Type" radio list
#        And I click the "Confirm" button
#        And I wait "3" seconds
#        And I click the "AddMeetingDetails" button
#        And I enter "Interview Team" in the "MeetingTitle" field
#        And I enter "Interview Discussion" in the "MeetingDescription" field
#        And I select "Interview Team" from the "team" dropdown
#        And I click the "Save" button
#        And I click "OK" in the confirmation box
#
#
#    @Sanity
#    Scenario: Verify Replan Start Time & End Time-> Booking a Conference Room Facility Offline
#        When I select "Conference Room" from the "SelectFacilityType" dropdown
#        And I click the "Facility Search" button
#        And I wait "8" seconds
#        Then I select the facility "conf 1"
#        And I enter "2018-02-27 18:00:00" in the "Start date" field
#        And I enter "2018-02-27 19:00:00" in the "End Date" field
#        And I click the "Start date" element
#        And I click the "BookNow" button
#        And I click the "Replan" button
#        And I click the "BookNow" button
#
#
#    @Sanity
#    Scenario: Verify Invalid Start Time & End Time-> Booking a Conference Room Facility Offline
#        When I select "Conference Room" from the "SelectFacilityType" dropdown
#        And I click the "Facility Search" button
#        And I wait "8" seconds
#        Then I select the facility "conf 1"
#        And I enter "2018-02-26 10:00:00" in the "Start date" field
#        And I enter "2018-02-26 11:00:00" in the "End Date" field
#        And I click the "Start date" element
#
#
#    @Sanity
#    Scenario: Verify Valid Start Time & End Time-> Booking a Training Room Facility Offline
#        When I select "Training Room" from the "SelectFacilityType" dropdown
#        And I click the "Facility Search" button
#        And I wait "8" seconds
#        Then I select the facility "training10"
#        And I enter "2018-02-26 10:00:00" in the "Start date" field
#        And I enter "2018-02-26 11:00:00" in the "End Date" field
#        And I click the "Start date" element
#        And I click the "BookNow" button
#        And I select "offline" from the "Payment Type" radio list
#        And I click the "Confirm" button
#        And I wait "3" seconds
#        And I click the "AddMeetingDetails" button
#        And I enter "Interview Team" in the "MeetingTitle" field
#        And I enter "Interview Discussion" in the "MeetingDescription" field
#        And I select "Interview Team" from the "team" dropdown
#        And I click the "Save" button
#        And I click "OK" in the confirmation box
#
#
#    @Sanity
#    Scenario: Verify Replan Start Time & End Time-> Booking a Training Room Facility Offline
#        When I select "Training Room" from the "SelectFacilityType" dropdown
#        And I click the "Facility Search" button
#        And I wait "8" seconds
#        Then I select the facility "training10"
#        And I enter "2018-02-27 10:00:00" in the "Start date" field
#        And I enter "2018-02-27 11:00:00" in the "End Date" field
#        And I click the "Start date" element
#        And I click the "BookNow" button
#        And I click the "Replan" button
#        And I click the "BookNow" button
#
#    @Sanity
#    Scenario: Verify Invalid Start Time & End Time-> Booking a Training Room Facility Offline
#        When I select "Training Room" from the "SelectFacilityType" dropdown
#        And I click the "Facility Search" button
#        And I wait "8" seconds
#        Then I select the facility "training10"
#        And I enter "2018-02-26 10:00:00" in the "Start date" field
#        And I enter "2018-02-26 11:00:00" in the "End Date" field
#        And I click the "Start date" element
#
#
#    @Sanity
#    Scenario: Verify Valid Start Time & End Time-> Booking a Meeting Room Facility Offline
#        When I select "Meeting Room" from the "SelectFacilityType" dropdown
#        And I click the "Facility Search" button
#        And I wait "8" seconds
#        Then I select the facility "meeting 10"
#        And I enter "2018-02-26 10:00:00" in the "Start date" field
#        And I enter "2018-02-26 11:00:00" in the "End Date" field
#        And I click the "Start date" element
#        And I click the "BookNow" button
#        And I select "offline" from the "Payment Type" radio list
#        And I click the "Confirm" button
#        And I wait "3" seconds
#        And I click the "AddMeetingDetails" button
#        And I enter "Interview Team" in the "MeetingTitle" field
#        And I enter "Interview Discussion" in the "MeetingDescription" field
#        And I select "Interview Team" from the "team" dropdown
#        And I click the "Save" button
#        And I click "OK" in the confirmation box
#
#
#    @Sanity
#    Scenario: Verify Replan Start Time & End Time-> Booking a Meeting Room Facility Offline
#        When I select "Conference Room" from the "SelectFacilityType" dropdown
#        And I click the "Facility Search" button
#        And I wait "8" seconds
#        Then I select the facility "meeting 10"
#        And I enter "2018-02-27 10:00:00" in the "Start date" field
#        And I enter "2018-02-27 11:00:00" in the "End Date" field
#        And I click the "Start date" element
#        And I click the "BookNow" button
#        And I click the "Replan" button
#        And I click the "BookNow" button
#
#    @Sanity
#    Scenario: Verify Invalid Start Time & End Time-> Booking a Meeting Room Facility Offline
#        When I select "Meeting Room" from the "SelectFacilityType" dropdown
#        And I click the "Facility Search" button
#        And I wait "8" seconds
#        Then I select the facility "conf 1"
#        And I enter "2018-02-26 10:00:00" in the "Start date" field
#        And I enter "2018-02-26 11:00:00" in the "End Date" field
#        And I click the "Start date" element
#
#
#    @Sanity
#    Scenario: Verify Upcoming Cancel Booking
#        When I click the "MyBookings" element
#        And I click the booking id "23"
#        And I click the "CancelBooking" button
#        And I click the "Proceed" button
#        Then the message "Booking with offline mode is cancelled" should be displayed
#        And I click the "OK" button
#
#
#    @Sanity
#    Scenario: Verify Reviews Valid & Invalid
#        When I click the "MyBookings" element
#        And I click the "History" button
#        And I click the booking id "2"
#        And I enter "Facility is not upto the mark" in the "Comments" field
#        And I click the "Submit" button
#        Then the message "Thank You for taking your time for the review" should be displayed
#        And I click the "OK" button
#
#
#    @Sanity
#    Scenario: Verify Logout
#        When I click the "UserProfile" element
#        And I click the "Logout" element
#
#
#
#
#
#
#
#
#
#
#
#
#
#
#
#
#
#
#
#
#
#
#
#
#
#
#
#
#
#
#
#
#
#
#
#
#
#
#
#
#
#
#
#
#
#
#
#
#
#
#
