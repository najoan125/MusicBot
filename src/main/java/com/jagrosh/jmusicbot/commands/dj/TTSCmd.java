/*
 * Copyright 2018 John Grosh <john.a.grosh@gmail.com>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jagrosh.jmusicbot.commands.dj;

import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jmusicbot.Bot;
import com.jagrosh.jmusicbot.audio.AudioHandler;
import com.jagrosh.jmusicbot.audio.QueuedTrack;
import com.jagrosh.jmusicbot.commands.MusicCommand;
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

/**
 *
 * @author John Grosh <john.a.grosh@gmail.com>
 */
public class TTSCmd extends MusicCommand
{
    public TTSCmd(Bot bot)
    {
        super(bot);
        this.name = "tts";
        this.help = "TTS를 재생합니다";
        this.arguments = "<text>";
        this.aliases = bot.getConfig().getAliases(this.name);
        this.bePlaying = false;
        this.beListening = true;
    }
    
    public boolean isplaying = false;
    AudioTrack audiotrack = null;

    @Override
    public void doCommand(CommandEvent event) 
    {
    	if (event.getArgs().isEmpty()) {
    		event.replyError("재생할 텍스트를 알려주세요. 사용법: `" + event.getClient().getPrefix() + "tts <text>`");
    		return;
    	}
    	AudioHandler handler = (AudioHandler)event.getGuild().getAudioManager().getSendingHandler();
        String args = "http://translate.google.com/translate_tts?ie=UTF-8&total=1&idx=0&textlen=32&client=tw-ob&q=" + event.getArgs().replace("%", "퍼센트").replace(" ", "%20").replace("\\", "역슬레시").replace("{", "여는중괄호").replace("}", "닫는중괄호").replace("#", "샾").replace("`", "%20").replace("\"", "큰따옴표").replace("+", "플러스").replace("|", "수직선").replace("^", "캐럿").replace("&", "앤드").replace("<", "%20").replace(">", "%20").replace("나죠안", "").replace("나죠아", "").replace("나조아", "").replace("나조안", "") + "&tl=ko-kr";
        bot.getPlayerManager().loadItemOrdered(event.getGuild(), args, new AudioLoadResultHandler() {

			@Override
			public void loadFailed(FriendlyException arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void noMatches() {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void playlistLoaded(AudioPlaylist arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void trackLoaded(AudioTrack track) {
				if (handler.getNowPlaying(event.getJDA()) != null) {
					handler.addTrackToFront(new QueuedTrack(track, event.getAuthor()));
					event.replySuccess("TTS를 대기열에 추가했습니다 (현재 재생중인 곡이 끝나면 TTS가 바로 재생됩니다)");
				}
				else {
					handler.addTrackToFront(new QueuedTrack(track, event.getAuthor()));
					event.replySuccess("TTS를 재생합니다");
				}
				
			}
        	
        });
    }
}
