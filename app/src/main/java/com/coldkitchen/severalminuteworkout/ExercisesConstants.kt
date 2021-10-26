package com.coldkitchen.severalminuteworkout

class ExercisesConstants {
    // Companion - to use items of it without create an object of a class:
    // exerciseList = ExercisesConstants.defaultExerciseList() in other kotlin file
    companion object{
        fun defaultExerciseList(): ArrayList<ExerciseModel>{
            val exerciseList = ArrayList<ExerciseModel>()

            val jumpingJacks = ExerciseModel(1, "Jumping Jacks",
                                            arrayOf(R.drawable.ic_jumping_jacks1,
                                                R.drawable.ic_jumping_jacks2,
                                                R.drawable.ic_jumping_jacks3,
                                                R.drawable.ic_jumping_jacks4),
                                            false, false)
            exerciseList.add(jumpingJacks)

            val wallSit = ExerciseModel(2, "Wall Sit",
                                        arrayOf(R.drawable.ic_wall_sit),
                                        false, false)
            exerciseList.add(wallSit)

            val pushUp = ExerciseModel(3, "Push Up",
                                        arrayOf(R.drawable.ic_push_up1,
                                            R.drawable.ic_push_up2),
                                        false, false)
            exerciseList.add(pushUp)

            val abdominalCrunch = ExerciseModel(4, "Abdominal Crunch",
                                                arrayOf(R.drawable.ic_abdominal_crunch1,
                                                R.drawable.ic_abdominal_crunch2),
                                                 false, false)
            exerciseList.add(abdominalCrunch)

            val stepUpOnChair = ExerciseModel(5, "Step-Up onto Chair\n" +
                    "(or equivalent)",
                                             arrayOf(R.drawable.ic_step_up_onto_chair1,
                                                 R.drawable.ic_step_up_onto_chair2,
                                                 R.drawable.ic_step_up_onto_chair3,
                                                 R.drawable.ic_step_up_onto_chair2,
                                                 R.drawable.ic_step_up_onto_chair1),
                                            false,false)
            exerciseList.add(stepUpOnChair)

            val squat = ExerciseModel(6, "Squat",
                                        arrayOf(R.drawable.ic_squat1,
                                        R.drawable.ic_squat2),
                                        false, false)
            exerciseList.add(squat)

            val tricepDipOnChair = ExerciseModel(7,"Triceps Dip On Chair\n(or equivalent)",
                                                arrayOf(R.drawable.ic_triceps_dip_on_chair1,
                                                R.drawable.ic_triceps_dip_on_chair2),
                                                false, false)
            exerciseList.add(tricepDipOnChair)

            val plank = ExerciseModel(8, "Plank",
                                        arrayOf(R.drawable.ic_plank),
                                        false, false)
            exerciseList.add(plank)

            val highKneesRunningInPlace = ExerciseModel(9, "High Knees Running In Place",
                                                        arrayOf(R.drawable.ic_high_knees_running_in_place1,
                                                        R.drawable.ic_high_knees_running_in_place2),
                                                        false, false)
            exerciseList.add(highKneesRunningInPlace)

            val lunges = ExerciseModel(10, "Lunges",
                                        arrayOf(R.drawable.ic_lunge1n3,
                                        R.drawable.ic_lunge2,
                                        R.drawable.ic_lunge1n3,
                                        R.drawable.ic_lunge4),
                                        false, false)
            exerciseList.add(lunges)

            val pushupAndRotation = ExerciseModel(11, "Push up and Rotation",
                                                    arrayOf(R.drawable.ic_push_up_and_rotation1n3,
                                                        R.drawable.ic_push_up_and_rotation2,
                                                        R.drawable.ic_push_up_and_rotation1n3,
                                                        R.drawable.ic_push_up_and_rotation4,
                                                        R.drawable.ic_pshuprot2_1n3,
                                                        R.drawable.ic_push_up_and_rotation2_2,
                                                        R.drawable.ic_pshuprot2_1n3,
                                                        R.drawable.ic_push_up_and_rotation2_4),
                                                    false, false)
            exerciseList.add(pushupAndRotation)

            val sidePlank = ExerciseModel(12, "Side Plank",
                                            arrayOf(R.drawable.ic_side_plank1,
                                            R.drawable.ic_side_plank2),
                                            false, false)
            exerciseList.add(sidePlank)

            return exerciseList
        }
    }
}