
## User Account System
- Two account types: Student accounts and Admin accounts
- Admin accounts will be pre-created with random 32-character tokens and generic passwords (like "changeme") -> maybe (this seems to be the simplest option)
- Admin users: Tom, Sister, (possibly) Lakendra, Krienke, Nowak
- Students create their own accounts during orientation or TAC first day of school
- Admin accounts also pre entered in adminAccountDatabase for ease of accesss

## Database Structure
- Student login database storing:
  - First name, last name
  - Grade (9-12)
  - Student ID
  - Email
  - Password
- Need to handle edge cases like students with identical names (possibll, highly doubt this will happen because they will be able to sign in with student ID anyways, which is another unique identifier)
- Include graduation year as a class identifier (maybe -> might just be overthinking --> See 'System Maintenance Requirements' below)

## Admin Features
- Admin homepage with menu bar and password change/reset option (to switch from generic 'changeme')
- Ability to view all students service submissions
- Accept/decline service submissions
- Email notifications when students submit service

## Student Features
- Student homepage showing:
  - Submitted service hours
  - Accepted service hours
  - Pending for approval service hours
- Account settings to update personal information
- Help section with instructions, about, creators etc etc (maybe)

## Service Verification Process
- Emails sent to supervisors/beneficiaries to verify service
- Supervisors can confirm or deny service completion
- Admins notified if service is denied
- Students notified if service is declined

## System Maintenance Requirements
- Backup functionality for the database (probably super important)
- Grade level auto-update logic at beginning of school year (maybe -> to differentiate service requirements (ie. freshmen and sophomores only need 5 services experience, whereas juniors and seniors need 7))
- Refresh all user service experience every beginning of school year (this could be as simple as just wiping the service database for every student sometime between the last week of august (beginnnig of school year) and the date the first experience is due)
- User deletion process for graduated seniors (i acutally dont know how to do this)
- Error handling for duplicate accounts (should never happen tho because all fields dont have to be unique but the student ID is always unique so even if edge case like same email, the student ID is the identifier)

## Technical Notes
- Already have a student login page and account creation form
- Need to integrate database logic with the form submission
- Database work is the current priority
- Consider email-based system for supervisor verification