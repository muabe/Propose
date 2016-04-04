package com.markjmind.propose3;

/**
 * <br>捲土重來<br>
 *
 * @author 오재웅(JaeWoong-Oh)
 * @email markjmind@gmail.com
 * @since 2013-09-14
 */
class DefalutAnimation {
	
//	private Propose proposePg;
//
//	protected DefalutAnimation(Propose proposePg){
//		this.proposePg = proposePg;
//	}
//
//	/**
//	 * JwMotion에서 사용하고 있는 애니메이션
//	 * @param pointX
//	 * @param motionSetH
//	 * @param pointY
//	 * @param motionSetV
//	 * @param duration
//	 * @return
//	 */
//	protected boolean startAnimation(JwPoint pointX, Motion motionSetH, JwPoint pointY, Motion motionSetV, long duration){
//		long endDurationH = 0;
//		long endDurationV = 0;
//		if(motionSetH!=null){
//			if(pointX.isForward()){
//				endDurationH = motionSetH.totalDuration;
//			}else{
//				endDurationH = 0L;
//			}
//		}
//		if(motionSetV!=null){
//			if(pointY.isForward()){
//				endDurationV = motionSetV.totalDuration;
//			}else{
//				endDurationV = 0L;
//			}
//		}
//		return this.startAnimation(pointX, motionSetH, endDurationH, pointY, motionSetV,endDurationV, duration);
//	}
//
//	/**
//	 * JwMotion에서 사용하고 있는 애니메이션<br>
//	 * 현재 남은 애니메이션의 duration만큼 끝까지 애니메이션을 play한다.<br>
//	 * fling에서 사용되는 애니메이션인지 아닌지를 구분한다.<br>
//	 * fling의 경우 가속도에 맞추어 애니메이션 되기때문에 duration으로 play하면 안되고<br>
//	 * 해당 남은 distance에 맞추어서 play해야한다.
//	 * @param motionSetH
//	 * @param isForwardH
//	 * @param motionSetV
//	 * @param isForwardV
//	 * @param isFling
//	 * @return
//	 */
//	protected boolean startAnimation(Motion motionSetH, boolean isForwardH, Motion motionSetV, boolean isForwardV, boolean isFling) {
//		proposePg.currX.setForward(isForwardH);
//		proposePg.currY.setForward(isForwardV);
//		if(isFling){
//			return this.startAnimation(proposePg.currX, motionSetH, proposePg.currY, motionSetV,-1);
//		}else{
//			return this.startAnimation(proposePg.currX, motionSetH, proposePg.currY, motionSetV,0);
//		}
//	}
//
//	/**
//	 * JwMotionSet에서 쓰고있는 애니메이션<br>
//	 * duration만큼 애니메이션을 play한다.
//	 * @param motionSet
//	 * @param isForward
//	 * @param duration
//	 */
//	protected void startAnimation(Motion motionSet, boolean isForward, long duration){
//		if(!proposePg.currAction.equals(ACTION.animation) && !proposePg.isMotionStart){
//			proposePg.startMotionEvent();
//			if((motionSet.getStatus().equals(STATUS.ready) && !isForward) || (motionSet.getStatus().equals(STATUS.end) && isForward)){
//				proposePg.endMotionEvent();
//			}else{
//				JwPoint point = proposePg.getDirectionPoint(motionSet.direction);
//				point.setForward(isForward);
//				point.setAcc(1.0f);
//				this.startAnimation(point,motionSet,null,null,duration);
//			}
//
//		}
//	}
//
//
//	protected void moveAnimation(Motion motionSet, float distance, long duration){
//		if(!proposePg.currAction.equals(ACTION.animation) && !proposePg.isMotionStart && motionSet.getCurrDistance() != distance){
//			proposePg.startMotionEvent();
//			if(distance<0f){
//				distance=0;
//			}else if(distance>motionSet.motionDistance){
//				distance = motionSet.motionDistance;
//			}
//			JwPoint point = proposePg.getDirectionPoint(motionSet.direction);
//			point.setAcc(1.0f);
//			if(motionSet.getCurrDistance()<distance){
//				point.setForward(true);
//			}else{
//				point.setForward(false);
//			}
//			long endDuration = motionSet.getDistanceToDuration(distance);
//			if(distance!=motionSet.getCurrDistance()){
//				this.startAnimation(point,motionSet,endDuration,null,null,0,duration);
//			}else{
//				proposePg.endMotionEvent();
//			}
//		}
//	}
//
//	/**
//	 * 뷰가 이동하든 아니든 enableMotionEvent 여부에 따라 이벤트 발생
//	 * @param motionSet
//	 * @param distance
//	 * @param enableMotionEvent
//	 */
//	protected void moveDistance(Motion motionSet, float distance, boolean enableMotionEvent){
//		if(!proposePg.currAction.equals(ACTION.animation) && !proposePg.isMotionStart){
//			if(enableMotionEvent){
//				proposePg.startMotionEvent();
//			}
//			if(distance<=0f){
//				distance=0f;
//			}else if(distance>=motionSet.motionDistance){
//				distance = motionSet.motionDistance;
//			}
//			if(motionSet.getCurrDistance() != distance) {
//				proposePg.moveDistance(motionSet, distance);
//			}
//			if(enableMotionEvent){
//				proposePg.endMotionEvent();
//			}
//		}
//	}
//	protected void moveDistance(Motion motionSet, float distance){
//		this.moveDistance(motionSet, distance, true);
//	}
//
//	/**
//	 * 애니메이션 방향에 대해 point에 미리 setForward를 해주어야한다.<br>
//	 * Forward된 방향에 맞게 애니메이션이 play된다.
//	 * @param pointX
//	 * @param motionSetH
//	 * @param endDurationH
//	 * @param pointY
//	 * @param motionSetV
//	 * @param endDurationV
//	 * @param duration
//	 * @return
//	 */
//	private boolean startAnimation(JwPoint pointX, Motion motionSetH, long endDurationH, JwPoint pointY, Motion motionSetV,long endDurationV, long duration){
//    		/*duration이 0보다 크면 사용자가 지정한 duration을 설정한다.*/
//			long accDurationX = duration, accDurationY = duration;
//			JwTimerAnimator timeAnim = new JwTimerAnimator();
//			boolean isRunX=false,isRunY=false;
//
//			if(motionSetH!=null && motionSetH.hasAnimation() && pointX.getAcc()>0f){
//				TimerValue tValueH = new TimerValue() {
//					@Override
//					public void onAnimationUpdate(long timeValue, HashMap<String,Object> params) {
//						Motion motionSet = (Motion)params.get("set");
//						JwPoint point = (JwPoint)params.get("point");
//						proposePg.moveDuration(point,motionSet,timeValue);
//					}
//				};
//				tValueH.addParam("point", pointX);
//				tValueH.addParam("set", motionSetH);
//				timeAnim.addTimerValue(tValueH);
//				if(endDurationH>motionSetH.totalDuration){
//					endDurationH = motionSetH.totalDuration;
//				}else if(endDurationH < 0){
//					endDurationH = 0;
//				}
//				tValueH.setValues(motionSetH.getCurrDuration(),endDurationH);
//				/*EnableDistancDuration이 false이면 거리로 환산하여 duration을 정하지 않음*/
//				if(duration==0 && !motionSetH.isEnableDuration()){
//					duration = -1;
//				}
//				if(pointX.isForward()){
//					if(!Motion.STATUS.end.equals(motionSetH.getStatus())){
//						if(duration<0){ /*duration이 0보다 작으면 거리를 환산하여 duration을 정한다.*/
//							accDurationX = (long)(motionSetH.motionDistance*(endDurationH-motionSetH.getCurrDuration())/motionSetH.totalDuration/pointX.getAcc());
//						}else if(duration==0){ /*duration이 0과 같으면 자동으로 남은 duration을 설정한다.*/
//							accDurationX = endDurationH-motionSetH.getCurrDuration();
//						}
//						if(accDurationX>0){
//							isRunX = true;
//						}
//					}
//				}else{
//					if(!Motion.STATUS.ready.equals(motionSetH.getStatus())){
//						if(duration<0){
//							accDurationX = (long)(motionSetH.motionDistance*(motionSetH.getCurrDuration()-endDurationH)/motionSetH.totalDuration/pointX.getAcc());
//						}else if(duration==0){
//							accDurationX = motionSetH.getCurrDuration()-endDurationH;
//						}
//						if(accDurationX>0){
//							isRunX = true;
//						}
//					}
//				}
//				motionSetH.setForward(pointX.isForward());
//				tValueH.setForward(pointX.isForward());
//			}
//			if(motionSetV!=null && motionSetV.hasAnimation() && pointY.getAcc()>0f){
//				TimerValue tValueV = new TimerValue() {
//					@Override
//					public void onAnimationUpdate(long timeValue, HashMap<String,Object> params) {
//						Motion motionSet = (Motion)params.get("set");
//						JwPoint point = (JwPoint)params.get("point");
//						proposePg.moveDuration(point,motionSet,timeValue);
//					}
//				};
//				tValueV.addParam("point", pointY);
//				tValueV.addParam("set", motionSetV);
//				timeAnim.addTimerValue(tValueV);
//				if(endDurationV>motionSetV.totalDuration){
//					endDurationV = motionSetV.totalDuration;
//				}else if(endDurationV < 0){
//					endDurationV = 0;
//				}
//				tValueV.setValues(motionSetV.getCurrDuration(),endDurationV);
//				/*EnableDistancDuration이 false이면 거리로 환산하여 duration을 정하지 않음*/
//				if(duration==0 && !motionSetV.isEnableDuration()){
//					duration = -1;
//				}
//				if(pointY.isForward()){
//					if(!Motion.STATUS.end.equals(motionSetV.getStatus())){
//						if(duration<0){
//							accDurationY = (long)(motionSetV.motionDistance*(endDurationV-motionSetV.getCurrDuration())/motionSetV.totalDuration/pointY.getAcc());
//						}else if(duration==0){
//							accDurationY = endDurationV-motionSetV.getCurrDuration();
//						}
//						if(accDurationY>0){
//							isRunY = true;
//						}
//					}
//				}else{
//					if(!Motion.STATUS.ready.equals(motionSetV.getStatus())){
//						if(duration<0){
//							accDurationY = (long)(motionSetV.motionDistance*motionSetV.getCurrDuration()/motionSetV.totalDuration/pointY.getAcc());
//						}else if(duration==0){
//							accDurationY = motionSetV.getCurrDuration()-endDurationV;
//						}
//						if(accDurationY>0){
//							isRunY = true;
//						}
//					}
//				}
//				motionSetV.setForward(pointY.isForward());
//				tValueV.setForward(pointY.isForward());
//			}
//			long accDuration = 0L;
//
//			if(isRunX && isRunY){
//				/*수평수직 둘다 애니메이션이 있을경우 가속도가 빠른곳을 기준으로 삼는다*/
//				if(Math.abs(pointX.getDiffPoint())<Math.abs(pointY.getDiffPoint())){
//					accDuration = accDurationY;
//				}else{
//					accDuration = accDurationX;
//				}
//			}else if(isRunX && !isRunY){
//				accDuration = accDurationX;
//				motionSetV = null;
//			}else if(!isRunX && isRunY){
//				accDuration = accDurationY;
//				motionSetH = null;
//			}else{
//				return false;
//			}
//			timeAnim.setDuration(accDuration);
//			proposePg.currAction = ACTION.animation;
//			timeAnim.setAnimatorListener(new TimeAnimationEvent());
//			timeAnim.start();
//			return true;
//	}
//
//    private class TimeAnimationEvent implements AnimatorListener{
//		@Override
//		public void onAnimationStart(Animator animation) {
//		}
//		@Override
//		public void onAnimationEnd(Animator animation) {
//			if(proposePg.isMotionStart){
//				proposePg.endMotionEvent();
//			}
//		}
//		@Override
//		public void onAnimationCancel(Animator animation) {
//		}
//		@Override
//		public void onAnimationRepeat(Animator animation) {
//		}
//
//    }
}
