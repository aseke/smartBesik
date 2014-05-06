//
//  controlPageViewController.h
//  besik
//
//  Created by Assylbek Murzakhmetov on 4/28/14.
//  Copyright (c) 2014 Assylbek Murzakhmetov. All rights reserved.
//

#import <UIKit/UIKit.h>
const static NSString *kAPIKeyR = @"CCA3E31CBABE40F8";
const static NSString *kRobotIdR = @"1098";
const static NSString *kAPIKeyU = @"6A87C4499E92481F";
const static NSString *kRobotIdU = @"1099";
//there are 8 fields that can be updated and a status can also be sent
//see http://www.myrobots.com/wiki/API for documentation
const static NSString *kRobotUpdateURLFormat =@"http://bots.myrobots.com/update?key=%@&field1=%@";
//the %@ after channels is the robot id and the key is needed if the robot is not public.
//see http://www.myrobots.com/wiki/API for documentation
const static NSString *kRobotRetrieveURLFormat =@"http://bots.myrobots.com/channels/%@/feed.json?key=%@";
@interface controlPageViewController : UIViewController
{
    int speed;
}
@property (weak, nonatomic) IBOutlet UILabel *humidity;
@property (weak, nonatomic) IBOutlet UILabel *temperature;
@property (weak, nonatomic) IBOutlet UIImageView *urine;
@property (weak, nonatomic) IBOutlet UIImageView *bottle;

@end
