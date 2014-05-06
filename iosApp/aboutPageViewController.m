//
//  aboutPageViewController.m
//  besik
//
//  Created by Assylbek Murzakhmetov on 4/28/14.
//  Copyright (c) 2014 Assylbek Murzakhmetov. All rights reserved.
//

#import "aboutPageViewController.h"

@implementation aboutPageViewController
- (IBAction)facebook:(id)sender {
    [[UIApplication sharedApplication] openURL:[NSURL URLWithString:@"http://facebook.com/smartbesik"]];
}
- (IBAction)twitter:(id)sender {
    [[UIApplication sharedApplication] openURL:[NSURL URLWithString:@"http://twitter.com/serikboldan"]];
}
- (IBAction)web:(id)sender {
    [[UIApplication sharedApplication] openURL:[NSURL URLWithString:@"smartbesik.kz"]];
}

@end
