// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Chassis;

public class TurnDegrees extends Command {
  private final Chassis m_chassis;
  private final double m_targetAngle;
  private final double m_speed;

  /** Creates a new TurnDegrees. */
  public TurnDegrees(double speed, double angleDegrees, Chassis chassis) {
    m_targetAngle = angleDegrees;
    m_speed = speed;
    m_chassis = chassis;
    addRequirements(chassis);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_chassis.arcadeDrive(0, 0);
    m_chassis.resetEncoders();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_chassis.arcadeDrive(0, m_speed);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_chassis.arcadeDrive(0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    /* Need to convert distance travelled to degrees. The Standard
       XRP Chassis found here, https://www.sparkfun.com/products/22230,
       has a wheel placement diameter (163 mm) - width of the wheel (8 mm) = 155 mm
       or 6.102 inches. We then take into consideration the width of the tires.
    */
    double inchPerDegree = Math.PI * 6.102 / 360;
    // Compare distance travelled from start to distance based on degree turn
    return getAverageTurningDistance() >= (inchPerDegree * m_targetAngle);
  }

  private double getAverageTurningDistance() {
    double leftDistance = Math.abs(m_chassis.getLeftDistanceInch());
    double rightDistance = Math.abs(m_chassis.getRightDistanceInch());
    return (leftDistance + rightDistance) / 2.0;
  }
}
