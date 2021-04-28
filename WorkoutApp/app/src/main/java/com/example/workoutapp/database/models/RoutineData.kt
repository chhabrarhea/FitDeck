package com.example.workoutapp.database.models

import com.example.workoutapp.R

object RoutineData  {
    private fun getObliqueExercises():ArrayList<Exercise>{
        val list=ArrayList<Exercise>()
        list.add(Exercise(1, "Side Plank", "Lie on your left side with your right leg stacked on top of your left leg, and your left forearm on the ground with your elbow underneath your shoulder.Your body should form a straight line from head to feet that makes about a 45-degree angle with the ground. Raise your right arm up toward the ceiling without letting your hips dip.", R.drawable.sp_o, isCompleted = false, isOngoing = false, 30))
        list.add(Exercise(2,"Standing Trunk Rotation", "Stand with your feet hip-width apart and hold a medicine ball between your hands. Keeping your core tight, your arms bent at right angles at the elbows, rotate your torso slowly to your right, keeping head and chest rotating along with it. Hold the twist briefly before rotating to your left.", R.drawable.sr_o,isCompleted = false,isOngoing = false,45))
        list.add(Exercise(3,"Standing Wood Chop", "Stand in a split stance with your right foot forward and your feet slightly apart. Hold a medicine ball in your hands and lift it to your right so it is slightly higher than shoulder height and extended diagonally away from the body. Then, in a fluid motion, bring the medicine ball toward your left hip.", R.drawable.wc_o,isCompleted = false,isOngoing = false,45))
        list.add(Exercise(4,"Russian Twist", "Sit on a mat with your legs slightly bent in front of you. Lean back so your torso and thighs form a V shape and lift your feet slightly off the ground. Hold your arms straight out in front of you, then twist your torso to one side in a controlled motion, tapping the floor before rotating to the other side and repeating.", R.drawable.rt_o,isCompleted = false,isOngoing = false,40))
        list.add(Exercise(5,"Mountain Climber","Begin in a plank position forming a straight line from head to toes parallel with the ground. Engage your core as you bring your right knee into your chest, keeping your right toes off the ground. Return to the plank position and repeat the move with your left knee.",R.drawable.mc_o,isCompleted = false,isOngoing = false,50))
        list.add(Exercise(6,"Side Bend","Start standing with your feet hip-width apart. Bend your upper body sideways toward the right. Pause and then return to standing while squeezing your left side obliques.",R.drawable.sb_o,isCompleted = false,isOngoing = false,30))
        list.add(Exercise(7,"Dead Bug","Lie on your back with your arms extended up toward the ceiling. Bend your knees so shins and thighs form a 90-degree angle. Engage your core and lower your right arm straight back behind you at the same time you extend your left leg out long in front of you, lowering it so it hovers just above the ground.",R.drawable.db_o,isCompleted = false,isOngoing = false,40))
        list.add(Exercise(8,"Extended Side Angle Pose","Place your hands on your hips with your feet in a wide stance. Turn your right foot so it’s pointing to your right. Bend your right knee and lower body down into a lunge position. Bend your right elbow and rest it on your right thigh, twisting your torso so it faces your left side, keeping the head aligned with your spine and also facing left. Lift your left arm overhead with your palm facing the floor and stretch it alongside your left ear.",R.drawable.sa_o,isCompleted = false,isOngoing = false,35))
        return list
    }
    private fun getFatBurningExercises():ArrayList<Exercise>{
        val list=ArrayList<Exercise>()
        list.add(Exercise(1,"Jumping Jacks","Start in a standing position with your arms resting at your sides. Slightly bend your knees and jump your legs out so they’re a little more than shoulder-width apart. At the same time, thrust your arms out and over your head.",R.drawable.jj_fb,isCompleted = false,isOngoing = false,30))
        list.add(Exercise(2,"Burpees","Stand with your feet shoulder-width apart. In one fluid motion, lower your body into a squat, place your hands on the ground in front of your feet, and jump your feet back so you land in a plank position. Then jump to return your feet to near your hands and complete a powerful jump straight up into the air.",R.drawable.b_fb,isCompleted = false,isOngoing = false,35))
        list.add(Exercise(3,"Squat Jumps","With your feet shoulder-width apart, lower your body into a squat position. Keep your core tight and launch into an explosive jump. Land lightly on your feet and immediately lower into a squat again.", R.drawable.sqj_fb,isCompleted = false,isOngoing = false,40))
        list.add(Exercise(4,"Skater Jumps","With your right foot planted, cross your left leg behind you and out to your right side. Simultaneously, swing your right arm out to about shoulder height and your left arm across your body reaching toward your right hip. Then jump to your left and repeat the movement on your left side.",R.drawable.skj_fb,isCompleted = false,isOngoing = false,30))
        list.add(Exercise(5,"Plank Jumps","Begin in a plank position with your wrists in line with your shoulders and your body extended in a straight line behind you. Your feet should be planted together on the floor. Jump your legs out wide, and then jump them back together at a quick pace.",R.drawable.pj_fb,isCompleted = false,isOngoing = false,30))
        list.add(Exercise(6,"High Knees","From a standing position, lift your left knee into your chest. Swiftly switch your legs. At a nonstop pace, keep alternating your knees and move your arms in a running motion.",R.drawable.hk_fb,isCompleted = false,isOngoing = false,40))
        return list
    }
    private fun getLegExercises():ArrayList<Exercise>{
        val list=ArrayList<Exercise>()
        list.add(Exercise(1,"Bodyweight Squats","Stand with your feet shoulder-width apart. Lower your hips and butt downward into a squat position. Keep your weight shifting back in your heels and your chest lifted up. Pause at the bottom and then drive up through the heels to stand.",R.drawable.ss_l,isCompleted = false,isOngoing = false,30))
        list.add(Exercise(2,"Dumbell Dead Lift","Hinge forward at the hips to lower your hands down the front of your legs, keeping the weights close to your body and tilting your back and upper body forward. Keep your back flat and maintain a slight bend in your knees.",R.drawable.dd_l,isCompleted = false,isOngoing = false,40))
        list.add(Exercise(3,"Alternating Lateral Lunge","Start standing with feet together. Step your right leg wide out to your right side, bending the right knee as your foot touches the ground. Squeeze your inner thighs together to push off of your right foot and return to standing.",R.drawable.ll_l,isCompleted = false,isOngoing = false,35))
        list.add(Exercise(4,"Calf Raises","Stand with your feet shoulder-width apart and your toes pointing straight ahead. Use your calf muscles to lift your heels off the floor. Pause at the top and then lower to the ground. ",R.drawable.cr_l,isCompleted = false,isOngoing = false,30))
        list.add(Exercise(5,"Reverse Lunges","Start standing with feet together. Step your right foot directly behind you. Lower your hips and drop your right knee and your right heel is lifted off the ground. Squeeze your glutes, quads, and calves as you press your left heel into the ground and bring your right leg forward to return to standing.",R.drawable.rl_l,isCompleted = false,isOngoing = false,30))
        list.add(Exercise(6,"Sumo Squats","Start by standing with your feet wider than hip-width apart and your toes pointed out at an angle of about 45 degrees. Bend your knees and lower your hips into a wide squat until your thighs are parallel to the ground, keeping chest lifted. Pause at the bottom and then push through your heels to return to standing. ",R.drawable.ss_l,isCompleted = false,isOngoing = false,35))
        list.add(Exercise(7,"Burpees","Stand with your feet shoulder-width apart. In one fluid motion, lower your body into a squat, place your hands on the ground in front of your feet, and jump your feet back so you land in a plank position.\nThen jump to return your feet to near your hands and complete a powerful jump straight up into the air.",R.drawable.b_l,isCompleted = false,isOngoing = false,40))
        return list
    }

    private val obliques=Routine(1,"Stronger Obliques",R.drawable.obliques_header,"The obliques work together to control movement in the spine, rib cage, and pelvis.",70,21, getObliqueExercises(),"#fa8d7c")
    private val fatBurning=Routine(2,"Fat Burning",R.drawable.fatburning_header,"Comfortable but challenging intensity for optimal fat burning.",80,30, getFatBurningExercises(),"#a4d3c3")
    private val legRoutine=Routine(3,"Legs",R.drawable.legs_header,"Target your thighs and calves for well defined legs.",80,10, getLegExercises(),"#4cc3bb")
    private val running=Routine(0,"Running",R.drawable.running,"",0,0,ArrayList(),"#4c7c94")
    val routines=ArrayList<Routine>(listOf(obliques, fatBurning, legRoutine, running))
}

