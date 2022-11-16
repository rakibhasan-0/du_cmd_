TIMEFORMAT = %R

for n in {1..100};
do
   time {
      ./mdu -j$n /pkg 
      echo "Threads: $n"
   }
done 