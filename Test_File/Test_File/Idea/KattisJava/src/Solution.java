import java.util.HashMap;

public class Solution {
    public int[] twoSum(int[] nums, int target) {

        HashMap<Integer, Integer> hash = new HashMap<Integer, Integer>();
        int [] sum = new int[2];
        for(int i = 0; i < nums.length; i++){
            hash.put(nums[i], i);
        }
        for(int i = 0; i < nums.length; i++){
            if(hash.containsKey(target-nums[i])){
                sum[0] = i;
                sum[1] = hash.get(target-nums[i]);
            }
        }
        return sum;
    }
}
