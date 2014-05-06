//
//  controlPageViewController.m
//  besik
//
//  Created by Assylbek Murzakhmetov on 4/28/14.
//  Copyright (c) 2014 Assylbek Murzakhmetov. All rights reserved.
//

#import "controlPageViewController.h"

@implementation controlPageViewController
- (IBAction)speedChange:(id)sender {
    speed++;
    if (speed>3) speed = 0;
    NSArray *speedValues = @[@"OFF",@"LOW",@"NORM",@"HIGH"];
    
    UIButton *button = (UIButton *)sender;
    [button setTitle:[speedValues objectAtIndex:speed] forState:UIControlStateNormal];
    
    
}

- (IBAction)autoMode:(id)sender {
    
}
- (IBAction)manualMode:(id)sender {
    
    NSString *valueToSend = [NSString stringWithFormat:@"%d",speed];
    
    NSString *url = [NSString stringWithFormat:@"http://bots.myrobots.com/update?key=%@&field1=%@&status=Hello", kAPIKeyU, valueToSend];
    NSLog(@"%@",url);
    NSURLRequest *request = [NSURLRequest requestWithURL:[NSURL URLWithString:url]];
    
    NSURLResponse *response;
    NSError *error;
    //send it synchronous
    NSData *responseData = [NSURLConnection sendSynchronousRequest:request returningResponse:&response error:&error];
    NSString *responseString = [[NSString alloc] initWithData:responseData encoding:NSUTF8StringEncoding];
    // check for an error. If there is a network error, you should handle it here.
    if(!error)
    {
        //log response
        NSLog(@"Response from server = %@", responseString);
    }
    valueToSend = [NSString stringWithFormat:@"%d",speed];
    
    url = [NSString stringWithFormat:@"http://bots.myrobots.com/update?key=%@&field5=%d&status=Hello", kAPIKeyR, (speed==0)?0:1];
    NSLog(@"%@",url);
    request = [NSURLRequest requestWithURL:[NSURL URLWithString:url]];
    
    
    //send it synchronous
    responseData = [NSURLConnection sendSynchronousRequest:request returningResponse:&response error:&error];
    responseString = [[NSString alloc] initWithData:responseData encoding:NSUTF8StringEncoding];
    // check for an error. If there is a network error, you should handle it here.
    if(!error)
    {
        //log response
        NSLog(@"Response from server = %@", responseString);
    }
}

-(void)viewDidLoad{
    speed = 0;
    NSString *retrievalURL = [NSString stringWithFormat:kRobotRetrieveURLFormat, kRobotIdR, kAPIKeyR];
    NSDictionary *retrievedData = [self parseJSONFileAtURL:retrievalURL];
    if(retrievedData!=nil){
        //dump all keys and values to see what was returned
        for (id key in retrievedData) {
            NSLog(@"key: %@, value: %@", key, [retrievedData objectForKey:key]);
        }
        if([retrievedData valueForKey:@"feeds"]!=nil){
            //just get the last item in the feeds, it is the latest
            if([((NSArray *)[retrievedData valueForKey:@"feeds"]) count]>0)
            {
                NSDictionary *latestFeedItem = [((NSArray *)[retrievedData valueForKey:@"feeds"]) lastObject];
                
                if(latestFeedItem!=nil){
                    if([latestFeedItem valueForKey:@"field1"]!=nil){
                        NSString *temp = [NSString stringWithFormat:@"%@", [latestFeedItem valueForKey:@"field1"]];
                        [self.temperature setText:[temp substringToIndex:4]];
                    }
                    if([latestFeedItem valueForKey:@"field2"]!=nil){
                        NSString *humi = [NSString stringWithFormat:@"%@", [latestFeedItem valueForKey:@"field2"]];
                        [self.humidity setText:[humi substringToIndex:4]];
                    }
                    if([latestFeedItem valueForKey:@"field4"]!=nil){
                        NSString *urine = [NSString stringWithFormat:@"%@", [latestFeedItem valueForKey:@"field4"]];
                        if ([urine isEqualToString:@"0.0"])
                        self.bottle.hidden = YES;
                        else self.bottle.hidden = NO;
                    }
                }
            }
            
        }
        
    }
    
    
}
- (NSDictionary *)parseJSONFileAtURL:(NSString *)URL
{
    
    NSString *agentString = @"Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_5_6; en-us) AppleWebKit/525.27.1 (KHTML, like Gecko) Version/3.2.1 Safari/525.27.1";
    NSMutableURLRequest *request = [NSMutableURLRequest requestWithURL:
                                    [NSURL URLWithString:URL]];
    [request setValue:agentString forHTTPHeaderField:@"User-Agent"];
    NSError *error;
    NSData *jsonData = [ NSURLConnection sendSynchronousRequest:request returningResponse: nil error: &error];
    
    if(error){
        NSLog(@"error getting feed %@", error);
        return nil;
    }
    
    
    //partly taken from http://stackoverflow.com/questions/5547311/how-do-i-parse-json-with-objective-c
    
    id object = [NSJSONSerialization
                 JSONObjectWithData:jsonData
                 options:0
                 error:&error];
    
    if(error) {
        NSLog(@"%@", error);
        return nil;
    }
    
    //returns an NSDictionary, which contains an array of arrays
    //see dump above in [self retrieveValue]
    //this is hackery, but good enough for an API test
    return (NSDictionary *)object;
}


@end
