// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Chassis;

public class DriveForDistance extends Command {
  private final Chassis m_chassis;
  private final double m_targetDistance;
  private final double m_speed;

  /** Creates a new DriveForDistance. */
  public DriveForDistance(double speed, double distanceInch, Chassis chassis) {
    m_targetDistance = distanceInch;
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
    m_chassis.arcadeDrive(m_speed, 0);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_chassis.arcadeDrive(0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return Math.abs(m_chassis.getAverageDistanceInch()) >= m_targetDistance;
  }
}
