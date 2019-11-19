# PPKWU_ZAD3

Get events of specific month in iCal format

returns string in iCal format
#URL
/calendar/events/{year}/{month}
#Method:
GET
###URL Params
Required:

year: number

month: number
###Data Params
None

###Success Response:
Code: 200 

Sample Call:
  $.ajax({
    url: "/calendar/events/2019/11")",
    dataType: "json",
    type : "GET",
    success : function(r) {
      console.log(r);
    }
  });
